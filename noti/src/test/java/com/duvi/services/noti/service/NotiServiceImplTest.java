package com.duvi.services.noti.service;

import com.duvi.services.noti.client.AccountClient;
import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.AccountDTO;
import com.duvi.services.noti.domain.dto.NotificationDTO;
import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;
import com.duvi.services.noti.domain.exception.NotificationAlreadyExists;
import com.duvi.services.noti.domain.exception.NotificationNotExists;
import com.duvi.services.noti.repository.NotiRepository;
import com.duvi.services.noti.service.impl.NotiServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotiServiceImplTest {
    @Mock
    private NotiRepository notiRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private NotiServiceImpl notiService;

    private static List<NotificationEntity> mockNotificationList = new ArrayList<>();
    @BeforeAll
    public static void setUp() {
        NotificationEntity n1 = new NotificationEntity(1L, "testAccount", "testAccount@gmail.com", Frequency.DAILY, NotiType.REMIND, true, LocalDate.now().minusDays(1));
        NotificationEntity n2 = new NotificationEntity(2L, "testAccount", "testAccount@gmail.com", Frequency.MONTHLY, NotiType.BACKUP, true, LocalDate.now().minusMonths(1));
        NotificationEntity n3 = new NotificationEntity(3L, "testAccount2", "testAccount2@gmail.com", Frequency.WEEKLY, NotiType.REMIND, true, LocalDate.now().minusDays(5));
        mockNotificationList.add(n1);
        mockNotificationList.add(n2);
        mockNotificationList.add(n3);
    }

    @Test
    public void createTest() throws NotificationAlreadyExists {

        Mockito.when(notiRepository.save(Mockito.any(NotificationEntity.class)))
                .then(invocationOnMock -> {
                    NotificationEntity savedNotification = invocationOnMock.getArgument(0);
                    savedNotification.setId(4L);
                    savedNotification.setLastNotified(LocalDate.now());
                    return savedNotification;
                });

        NotificationDTO dto = new NotificationDTO("createdAccount", "createdAccount@gmail.com", Frequency.DAILY, NotiType.REMIND);
        NotificationEntity notificationCreated = notiService.createNotification(dto);
        assertEquals(4, notificationCreated.getId());
    }
    @Test
    public void createException() throws NotificationAlreadyExists {
        NotificationDTO dto = new NotificationDTO("createdAccount", "createdAccount@gmail.com", Frequency.DAILY, NotiType.REMIND);
        Mockito.when(notiRepository.existsByAccountNameAndNotiType(dto.accountName(), dto.notiType())).thenReturn(true);

        assertThrows(NotificationAlreadyExists.class, () -> {
            notiService.createNotification(dto);
        });
    }

    @Test
    public void getByNameTest() throws NotificationNotExists {
        Mockito.when(notiRepository.findAllByAccountName(Mockito.any(String.class))).then(invocationOnMock -> {
            String name = invocationOnMock.getArgument(0);
            List<NotificationEntity> notificationsForName = mockNotificationList.stream().filter(n -> Objects.equals(n.getAccountName(), name)).toList();
            return notificationsForName;
        });

        String name = "testAccount";
        List<NotificationEntity> notifications = notiService.getNotificationsByName(name);

        assertTrue(notifications.stream().filter( n -> !Objects.equals(n.getAccountName(), name))
                .toList()
                .isEmpty());
    }
    @Test
    public void getByNameExceptionTest() throws NotificationNotExists {
        String name = "doesNotExist";
        Mockito.when(notiRepository.existsByAccountName(name)).thenReturn(false);

        assertThrows(NotificationNotExists.class, () -> {
            notiService.getNotificationsByName(name);
        });
    }

    @Test
    public void deleteTest() throws NotificationNotExists {
        NotificationDTO dtoToDelete = new NotificationDTO("testAccount2", "testAccount2@gmail.com", Frequency.DAILY, NotiType.REMIND);
        Mockito.when(notiRepository.deleteByAccountNameAndNotiType("testAccount2", NotiType.valueOf("REMIND")))
                .then(invocationOnMock -> {
                    mockNotificationList = mockNotificationList.stream().filter( n ->
                            !Objects.equals(n.getAccountName(), dtoToDelete.accountName()) && !Objects.equals(n.getNotiType(), dtoToDelete.notiType())
                    ).toList();
                    return 1;
                });

        int sizeBefore = mockNotificationList.size();
        notiService.deleteNotification(dtoToDelete);

        assertTrue(sizeBefore > mockNotificationList.size());
    }

    @Test
    public void deleteException() throws NotificationNotExists {
        NotificationDTO dtoToDelete = new NotificationDTO("doesNotExist", "doesNotExist@gmail.com", Frequency.DAILY, NotiType.REMIND);
        assertThrows(NotificationNotExists.class, () -> {
            notiService.deleteNotification(dtoToDelete);
        });
    }

    @Test
    public void sendRemindNotificationsTest() throws MessagingException, IOException {
        Mockito.when(notiRepository.findAllRemindReady()).then(invocationOnMock -> {
            return mockNotificationList.stream().filter(notification ->
                    Objects.equals(notification.getNotiType(), NotiType.REMIND) &&
                            notification.getActive() &&
                            !LocalDate.now().isBefore(notification.getLastNotified().plusDays(notification.getFrequency().getDays()))
            ).toList();
        });

        Mockito.when(notiRepository.save(Mockito.any(NotificationEntity.class))).then(invocationOnMock -> {
            NotificationEntity notified = invocationOnMock.getArgument(0);
            mockNotificationList = mockNotificationList.stream().peek(n -> {
                if (Objects.equals(n.getId(), notified.getId())) {
                    n.setLastNotified(LocalDate.now());
                }
            }).toList();
            return notified;
        });

        Integer readyBeforeSending = notiService.sendRemindNotifications();
        Integer readyAfterSending = notiService.sendRemindNotifications();

        assertTrue(readyBeforeSending > readyAfterSending);
        Mockito.verify(emailService, times(readyBeforeSending)).sendEmail(any(Recipient.class), eq(NotiType.REMIND), any());
    }

    @Test
    public void sendBackupNotificationsTest() throws MessagingException, IOException {
        Mockito.when(notiRepository.findAllBackupReady()).then(invocationOnMock -> {
            return mockNotificationList.stream().filter(notification ->
                    Objects.equals(notification.getNotiType(), NotiType.BACKUP) &&
                            notification.isActive() &&
                            !LocalDate.now().isBefore(notification.getLastNotified().plusDays(notification.getFrequency().getDays()))
            ).toList();
        });

        Mockito.when(notiRepository.save(Mockito.any(NotificationEntity.class))).then(invocationOnMock -> {
            NotificationEntity notified = invocationOnMock.getArgument(0);
            mockNotificationList = mockNotificationList.stream().peek(n -> {
                if (Objects.equals(n.getId(), notified.getId())) {
                    n.setLastNotified(LocalDate.now());
                }
            }).toList();
            return notified;
        });

        Mockito.when(accountClient.getAccount(anyString())).then(invocationOnMock -> {
            String accountName = invocationOnMock.getArgument(0);
            return new AccountDTO(accountName, LocalDateTime.now(), "note", "icon", null, null);
        });

        Integer readyBeforeSending = notiService.sendBackupNotifications();
        Integer readyAfterSending = notiService.sendBackupNotifications();

        assertTrue(readyBeforeSending > readyAfterSending);
        Mockito.verify(emailService, times(readyBeforeSending)).sendEmail(any(Recipient.class), eq(NotiType.BACKUP), any(String.class));
        Mockito.verify(accountClient, times(readyBeforeSending)).getAccount(anyString());
    }

    @Test
    public void whenNotReady_dontSendRemindEmail() throws MessagingException, IOException {
        Mockito.when(notiRepository.findAllRemindReady()).thenReturn(new ArrayList<>());

        Integer readyBeforeSending = notiService.sendRemindNotifications();
        Integer readyAfterSending = notiService.sendRemindNotifications();

        assertEquals(0, (int) readyBeforeSending);
        Mockito.verify(emailService, times(0)).sendEmail(any(Recipient.class), eq(NotiType.REMIND), any());
    }

    @Test
    public void whenNotReady_dontSendBackupEmail() throws MessagingException, IOException {
        Mockito.when(notiRepository.findAllBackupReady()).thenReturn(new ArrayList<>());

        Integer readyBeforeSending = notiService.sendBackupNotifications();
        Integer readyAfterSending = notiService.sendBackupNotifications();

        assertEquals(0, (int) readyBeforeSending);
        Mockito.verify(emailService, times(0)).sendEmail(any(Recipient.class), eq(NotiType.BACKUP), any());
    }

}

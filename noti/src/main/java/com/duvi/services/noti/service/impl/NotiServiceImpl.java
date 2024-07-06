package com.duvi.services.noti.service.impl;

import  com.duvi.services.noti.client.AccountClient;
import com.duvi.services.noti.client.StatsClient;
import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.AccountDTO;
import com.duvi.services.noti.domain.dto.NotificationDTO;
import com.duvi.services.noti.domain.enums.NotiType;
import com.duvi.services.noti.domain.exception.NotificationAlreadyExists;
import com.duvi.services.noti.domain.exception.NotificationNotExists;
import com.duvi.services.noti.repository.NotiRepository;
import com.duvi.services.noti.service.EmailService;
import com.duvi.services.noti.service.NotiService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotiServiceImpl implements NotiService {
    private Logger logger = LoggerFactory.getLogger(NotiServiceImpl.class);

    private NotiRepository notiRepository;
    private StatsClient statsClient;
    private AccountClient accountClient;
    private EmailService emailService;

    public NotiServiceImpl(NotiRepository notiRepository, StatsClient statsClient, AccountClient accountClient, EmailService emailService) {
        this.notiRepository = notiRepository;
        this.statsClient = statsClient;
        this.accountClient = accountClient;
        this.emailService = emailService;
    }

    @Override
    public NotificationEntity createNotification(NotificationDTO dto) throws NotificationAlreadyExists {
        NotificationEntity createdNoti = new NotificationEntity(dto);
        if (notiRepository.existsByAccountNameAndNotiType(dto.accountName(), dto.notiType())) {
            throw new NotificationAlreadyExists(dto.accountName(), dto.notiType());
        }
        return notiRepository.save(createdNoti);
    }

    @Override
    public List<NotificationEntity> getNotificationsByName(String accountName) throws NotificationNotExists {
        if (!notiRepository.existsByAccountName(accountName)) {
            throw new NotificationNotExists(accountName);
        }
        return notiRepository.findAllByAccountName(accountName);
    }

    @Override
    public void deleteNotification(NotificationDTO dto) throws NotificationNotExists {
        if (!notiRepository.existsByAccountNameAndNotiType(dto.accountName(), dto.notiType())) {
           throw new NotificationNotExists(dto.accountName(), dto.notiType());
        }
        notiRepository.deleteByAccountNameAndNotiType(dto.accountName(), dto.notiType());
    }

    @Override
    @Scheduled(cron = "${remind.cron}")
    public Integer sendRemindNotifications() throws MessagingException {

        List<NotificationEntity> notifications = notiRepository.findAllRemindReady();

        logger.info("found {} remind notifications ready to be sent!", notifications.size());

        notifications.forEach((noti) ->
                {
                    Recipient recipient = new Recipient(noti.getAccountName(), noti.getEmail(), noti.getFrequency().getFrequencyValue());
                    try {
                        emailService.sendEmail(recipient, NotiType.REMIND, null);
                        noti.setLastNotified(LocalDate.now());
                        notiRepository.save(noti);
                        logger.info("Remind notification sent to {} successfully", noti.getEmail());
                    } catch (MessagingException | IOException e) {
                        logger.info("Exception while sending remind notification to {}", noti.getEmail());
                    }
                }
        );

        return notifications.size();
    }

    @Override
    @Scheduled(cron = "${backup.cron}")
    public Integer sendBackupNotifications() throws MessagingException {
        List<NotificationEntity> notifications = notiRepository.findAllBackupReady();

        logger.info("found {} backup notifications ready to be sent!", notifications.size());

        notifications.forEach((noti) ->
                {
                    Recipient recipient = new Recipient(noti.getAccountName(), noti.getEmail(), noti.getFrequency().getFrequencyValue());
                    AccountDTO accountDTO = accountClient.getAccount(noti.getAccountName());
                    try {
                        emailService.sendEmail(recipient, NotiType.BACKUP, accountDTO.toString());
                        noti.setLastNotified(LocalDate.now());
                        notiRepository.save(noti);
                        logger.info("Backup notification sent to {} successfully", noti.getEmail());
                    } catch (MessagingException | IOException e) {
                        logger.info("Exception while sending backup notification to {}", noti.getEmail());
                    }
                }
        );

        return notifications.size();


    }
}

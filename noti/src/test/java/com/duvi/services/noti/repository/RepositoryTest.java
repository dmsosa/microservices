package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.NotificationEntity;

import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private NotiRepository notiRepository;

    @Test
    @DisplayName("find all remind ready")
    public void returnAllRemindNotificationsReady() {
        List<NotificationEntity> notifications = notiRepository.findAllRemindReady();
        assertThat(Objects.equals(notifications.size(), 2)).isTrue();
    }

    @Test
    @DisplayName("find all backup ready")
    public void returnAllBackupNotificationsReady() {
        List<NotificationEntity> notifications = notiRepository.findAllBackupReady();
        assertThat(Objects.equals(notifications.size(), 1)).isTrue();
    }
    @Test
    @DisplayName("non-active notifications are not returned by repository")
    public void nonActiveNotificationsAreNotReturned() {
        List<NotificationEntity> remindNotifications = notiRepository.findAllRemindReady();
        List<NotificationEntity>  backupNotifications = notiRepository.findAllBackupReady();
        Boolean allActive = true;
        for (NotificationEntity noti : remindNotifications ) {
            if (!noti.getActive()) {
                allActive = false;
                break;
            }
        }
        for (NotificationEntity noti : backupNotifications ) {
            if (!noti.getActive()) {
                allActive = false;
                break;
            }
        }
        assertThat(allActive).isTrue();
    }


    @Test
    @DisplayName("Unique constraint Name-Type")
    public void uniqueConstraintAccountNameNotiType() {
        NotificationEntity alreadyExistsNoti = new NotificationEntity("demo", "demo@test.com", Frequency.DAILY, NotiType.REMIND);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            notiRepository.save(alreadyExistsNoti);
        });
    }

    @Test
    @DisplayName("delete not found")
    public void deleteNotFound() {
        Integer deletedEntities = notiRepository.deleteByAccountNameAndNotiType("demo", NotiType.REMIND);
        assertTrue(Objects.equals(deletedEntities, 0));
    }
}

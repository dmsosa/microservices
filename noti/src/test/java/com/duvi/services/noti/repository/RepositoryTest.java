package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Objects;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private NotiRepository notiRepository;

    @Test
    @DisplayName("all ready remind")
    public void returnAllRemindNotificationsReady() {
        List<NotificationEntity> notifications = notiRepository.findAllRemindReady();
        assertThat(Objects.equals(notifications.size(), 2)).isTrue();
    }

    @Test
    @DisplayName("non-active notifications are not returned by repository")
    public void nonActiveNotificationsAreNotReturned() {
        List<NotificationEntity> notifications = notiRepository.findAllRemindReady();
        Boolean allActive = true;
        for (NotificationEntity noti : notifications ) {
            if (!noti.getActive()) {
                allActive = false;
            }
        }
        assertThat(allActive).isTrue();
    }

    @Test
    @DisplayName("all ready backup")
    public void returnAllBackupNotificationsReady() {
        List<NotificationEntity> notifications = notiRepository.findAllBackupReady();
        assertThat(Objects.equals(notifications.size(), 1)).isTrue();
    }
}

package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.dto.NotificationDTO;
import com.duvi.services.noti.domain.exception.NotificationAlreadyExists;
import com.duvi.services.noti.domain.exception.NotificationNotExists;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface NotiService {

    NotificationEntity createNotification(NotificationDTO dto) throws NotificationAlreadyExists;
    List<NotificationEntity> getNotificationsByName(String accountName) throws NotificationNotExists;

    void deleteNotification(NotificationDTO dto) throws NotificationNotExists;
    Integer sendRemindNotifications() throws MessagingException, IOException;
    Integer sendBackupNotifications() throws MessagingException, IOException;
}

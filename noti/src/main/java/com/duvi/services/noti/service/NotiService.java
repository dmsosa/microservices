package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.dto.NotificationDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotiService {

    NotificationEntity createNotification(NotificationDTO dto);
    List<NotificationEntity> getNotificationsByName(String accountName);

    NotificationEntity deleteNotification(NotificationDTO dto);
    void sendRemindNotifications() throws MessagingException;
    void sendBackupNotifications() throws MessagingException;
}

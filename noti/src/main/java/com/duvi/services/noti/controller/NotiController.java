package com.duvi.services.noti.controller;

import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.dto.NotificationDTO;
import com.duvi.services.noti.domain.exception.NotificationAlreadyExists;
import com.duvi.services.noti.domain.exception.NotificationNotExists;
import com.duvi.services.noti.service.NotiService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
public class NotiController {
    private NotiService notiService;
    public NotiController(NotiService notiService) {
        this.notiService = notiService;
    }

    @PostMapping(path = "")
    public ResponseEntity<NotificationEntity> createNotifications(@RequestBody NotificationDTO dto) throws NotificationAlreadyExists {
        NotificationEntity createdNoti = notiService.createNotification(dto);
        return new ResponseEntity<>(createdNoti, HttpStatus.OK);
    };
    @GetMapping(path = "/{accountName}")
    public ResponseEntity<List<NotificationEntity>> getNotification(@PathVariable String accountName) throws NotificationNotExists {
        List<NotificationEntity> notifications = notiService.getNotificationsByName(accountName);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    };
    @GetMapping(path = "/send")
    public ResponseEntity<Void> sendNotification() throws MessagingException, IOException {
        notiService.sendRemindNotifications();
        return new ResponseEntity<>(HttpStatus.OK);
    };


    @DeleteMapping(path = "")
    public ResponseEntity<Void> deleteNotification(@RequestBody NotificationDTO dto) throws NotificationNotExists {
        notiService.deleteNotification(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    };

}

package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.enums.NotiType;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface EmailService {
    void sendEmail(Recipient recipient, NotiType type, String attachment) throws MessagingException, IOException;
}

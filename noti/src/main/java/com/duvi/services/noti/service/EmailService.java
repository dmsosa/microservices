package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(Recipient recipient, NotiType type, String attachment) throws MessagingException;
}

package com.duvi.services.noti.service.impl;

import com.duvi.services.noti.client.AccountClient;
import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.service.EmailService;
import com.duvi.services.noti.service.NotiService;
import com.duvi.services.noti.service.RecipientService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotiServiceImpl implements NotiService {
    private Logger logger = LoggerFactory.getLogger(NotiServiceImpl.class);

    private AccountClient accountClient;
    private EmailService emailService;
    private RecipientService recipientService;

    public NotiServiceImpl(AccountClient accountClient, EmailService emailService, RecipientService recipientService) {
        this.accountClient = accountClient;
        this.emailService = emailService;
        this.recipientService = recipientService;
    }

    @Override
    @Scheduled(cron = "${remind.cron}")
    public void sendRemindNoti() {
        List<Recipient> recipients = recipientService.findReadyToNoti(NotiType.REMIND);
        logger.info("Found {} recipients for REMIND notifications, sending...", recipients.size());

        recipients.forEach(recipient -> {
            try {
                emailService.sendEmail(recipient, NotiType.REMIND, null);
                recipientService.MarkNotified(recipient, NotiType.REMIND);
            } catch (MessagingException e) {
                logger.error("Something went wrong while sending {} notification to {}", NotiType.REMIND, recipient.getAccountName(), e);
                throw new RuntimeException(e);
            }
        });

        logger.info("All the remind notifications were sent");
    }

    @Override
    public void sendBackupNoti() {
        List<Recipient> recipients = recipientService.findReadyToNoti(NotiType.REMIND);
        logger.info("Found {} recipients for BACKUP notifications, sending...", recipients.size());

        recipients.forEach(recipient -> {
            try {
                String attachment = accountClient.getAccount(recipient.getAccountName());
                emailService.sendEmail(recipient, NotiType.BACKUP, attachment);
                recipientService.MarkNotified(recipient, NotiType.REMIND);
            } catch (MessagingException e) {
                logger.error("Something went wrong while sending {} notification to {}", NotiType.REMIND, recipient.getAccountName(), e);
                throw new RuntimeException(e);
            }
        });

        logger.info("All the backup notifications were sent");
    }
}

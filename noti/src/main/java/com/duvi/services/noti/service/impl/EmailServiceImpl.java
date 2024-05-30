package com.duvi.services.noti.service.impl;

import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.enums.NotiType;
import com.duvi.services.noti.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JavaMailSender javaMailSender;
    private Environment env;
    public EmailServiceImpl(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
    }

    @Override
    public void sendEmail(Recipient recipient, NotiType type, String attachment) throws MessagingException {
        final String subject = env.getProperty(type.getSubject());
        final String text = MessageFormat.format(Objects.requireNonNull(env.getProperty(type.getText())), recipient.getAccountName(), recipient.getFrequencyValue());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setTo(recipient.getEmail());

        if (StringUtils.hasLength(attachment)) {
            helper.addAttachment(env.getProperty(type.getAttachment()), new ByteArrayResource(attachment.getBytes()));
        }
        javaMailSender.send(message);

        logger.info("{} email notification sent to {} successfully", type, recipient.getAccountName());
    }

}

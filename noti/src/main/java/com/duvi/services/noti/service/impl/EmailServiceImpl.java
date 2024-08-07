package com.duvi.services.noti.service.impl;

import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.enums.NotiType;
import com.duvi.services.noti.service.EmailService;
import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private Resource remindTemplate = new ClassPathResource("static/templates/remind.html");
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JavaMailSender javaMailSender;
    private Environment env;
    public EmailServiceImpl(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
    }

    @Override
    public void sendEmail(Recipient recipient, NotiType type, @Nullable String attachment) throws MessagingException, IOException {
        String subject = env.getProperty(type.getSubject());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        message.setContent(remindTemplate.getContentAsString(StandardCharsets.UTF_8), "text/html; charset=utf-8");
        helper.setSubject(subject);
        helper.setTo(recipient.getEmail());

        if (Objects.nonNull(attachment) && Objects.equals(type, NotiType.BACKUP)) {
            helper.addAttachment(env.getProperty(type.getAttachment()), new ByteArrayResource(attachment.getBytes()));
        }
        javaMailSender.send(message);

        logger.info("{} email notification sent to {} successfully", type, recipient.getAccountName());
    }

}

package com.duvi.services.noti.service.impl;

import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class EmailServiceImpl  implements EmailService {
    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender javaMailSender;
    private Environment env;
    public EmailServiceImpl(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
    }
    @Override
    public void sendEmail(Recipient recipient, NotiType type, String attachment) throws MessagingException {
        String subject = env.getProperty(type.getSubject());
        MessageFormat textFormat = new MessageFormat(env.getProperty(type.getText());
        final String text = textFormat.format(recipient.getAccountName());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipient.getEmail());
        helper.setSubject(subject);
        helper.setText(text);

        if (attachment != null && attachment.length() > 0) {
            helper.addAttachment(env.getProperty(type.getAttachment()), new ByteArrayResource(attachment.getBytes()));
        }

        javaMailSender.send(message);

        logger.info("{} notification successfully sent to recipient {} via email", type.name(), recipient.getAccountName());
    }
}

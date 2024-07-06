package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;
import com.duvi.services.noti.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {
    @Mock
    private static JavaMailSender javaMailSender;

    @Mock
    private Environment env;

    @Captor
    private ArgumentCaptor<MimeMessage> messageCaptor;

    @InjectMocks
    private EmailServiceImpl emailService;

    private static Recipient anyRecipient;

    private static final Resource remindTemplate = new ClassPathResource("static/templates/remind.html");


    @BeforeAll
    public static void setUp() {
        anyRecipient = new Recipient("anyRecipient", "anyRecipient@gmail.com", Frequency.DAILY.name());
    }

    @Test
    public void shouldSendRemindEmail() throws MessagingException, IOException {
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getDefaultInstance(new Properties())));

        Recipient anyRecipient = new Recipient("anyRecipient", "anyRecipient@gmail.com", Frequency.DAILY.name());

        String subject = "This is a TEST for Remind Notifications";


        Mockito.when(env.getProperty(NotiType.REMIND.getSubject())).thenReturn(subject);

        emailService.sendEmail(anyRecipient, NotiType.REMIND, null);

        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage sentMessage = messageCaptor.getValue();


        assertEquals(subject, sentMessage.getSubject());
        assertEquals(remindTemplate.getContentAsString(StandardCharsets.UTF_8), sentMessage.getContent());

    }
    @Test
    public void shouldSendBackupEmail() throws MessagingException, IOException {
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getDefaultInstance(new Properties())));

        String subject = "This is a TEST for Backup Notifications";
        String attachment = "testAttachment.json";


        Mockito.when(env.getProperty(NotiType.BACKUP.getSubject())).thenReturn(subject);
        Mockito.when(env.getProperty(NotiType.BACKUP.getAttachment())).thenReturn(attachment);

        emailService.sendEmail(anyRecipient, NotiType.BACKUP, "{\"name\": \"accountDTO\"}");

        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage sentMessage = messageCaptor.getValue();
        String recipientEmail = Arrays.stream(sentMessage.getAllRecipients()).findFirst().get().toString();

        //todo check other fields
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(recipientEmail, anyRecipient.getEmail());


    }
}

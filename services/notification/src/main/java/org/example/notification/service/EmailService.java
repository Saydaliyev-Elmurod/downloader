package org.example.notification.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendHtmlMessage(String to) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper;

            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("subject");
            helper.setText("htmlBody", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error send msg to email");
        }


    }
}

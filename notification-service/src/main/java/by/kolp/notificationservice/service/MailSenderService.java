package by.kolp.notificationservice.service;


import by.kolp.notificationservice.model.dto.AdminEmailRequest;
import by.kolp.notificationservice.model.dto.EmailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MailSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.mail.username}")
    private String from;

    public MailSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(AdminEmailRequest email) {
        rabbitTemplate.convertAndSend("emailQueue", email);
        log.info("Email sent to queue");
    }

    public EmailResult send(Page<String> recipients, String subject, String text) {
        int failedEmails = 0;
        int successfulEmails = 0;
        List<String> failedAddresses = new ArrayList<>();

        for (String email : recipients) {
            if (email == null || email.isEmpty()){
                log.error("Email is null or empty: {}", email);
                continue;
            }

            try {
                sendSingleEmail(email, subject, text);
                successfulEmails++;
                log.info("Email sent to " + email);
            }catch (MailException e) {
                log.error("Failed to send to: {}" , email, e);
                failedAddresses.add(email);
                failedEmails++;}}

        return new EmailResult(failedEmails, successfulEmails, failedAddresses);
    }

    private void sendSingleEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
    }
}

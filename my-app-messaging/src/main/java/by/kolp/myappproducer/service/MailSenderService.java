package by.kolp.myappproducer.service;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MailSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailSenderService(JavaMailSender mailSender, RabbitTemplate rabbitTemplate) {
        this.mailSender = mailSender;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(AdminEmailRequest email) {
        rabbitTemplate.convertAndSend("mailQueue", email);
        log.info("Email sent to queue");
    }


    public String send(List<String> emails, String subject, String text) {

        if (emails == null || emails.isEmpty()) {
            log.warn("Email list is empty, nothing to send");
            return "List of emails is empty";
        }

        int failedEmails = 0;
        int successfulEmails = 0;

        for (String email : emails) {
            if(email == null || email.isBlank()) {
                log.warn("No recipients provided");
                continue;
            }


        try{
            SimpleMailMessage message = createMessage(email, subject, text);
            mailSender.send(message);
            successfulEmails++;
            log.info("Successfully sent to: {}", email);
        }catch (MailException e){
            log.error("Failed sent to: {}", email, e);
            failedEmails++;
            }
        }

        return "Failed attemps: " + failedEmails + ", successful emails: " + successfulEmails;
    }

    private SimpleMailMessage createMessage(String to, String subject, String text) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return message;

    }




}

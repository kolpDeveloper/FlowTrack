package by.kolp.myappproducer.service;

import by.kolp.myappcore.repository.interfaces.UserRepository;
import by.kolp.myappproducer.dto.AdminEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String from;

    public MailSenderService(JavaMailSender mailSender, RabbitTemplate rabbitTemplate, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    public void sendToQueue(AdminEmailRequest email) {
        rabbitTemplate.convertAndSend("mailQueue", email);
        log.info("Email sent to queue");
    }


    @RabbitListener
    public String send(Page<String> emails, String subject, String text) {

        if (emails == null) {
            log.warn("Email list is empty, nothing to send");
            return "List of emails is empty";
        }

        int failedEmails = 0;
        int successfulEmails = 0;
        Page<String> emailsList = userRepository.findAllEmails(PageRequest.of(0,10));

        for (String email : emailsList) {
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
            return message;

    }




}

package by.kolp.myappproducer.service;

import by.kolp.myappproducer.dto.EmailSendingResult;
import by.kolp.myappproducer.dto.SubjectMessageDTO;
import by.kolp.myappproducer.model.repository.MessageRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final JavaMailSender mailSender;
    private final RabbitTemplate rabbitTemplate;

    public Page<String> findAllEmails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        return messageRepository.findAllEmails(pageable);
    }


    private void sendToQueue(SubjectMessageDTO message) {
        log.info("Sending message to queue {}", message.subject());
        rabbitTemplate.convertAndSend(message);
    }



    public EmailSendingResult sendHtmlMessage(String subject, String htmlContent){
        int page = 0;
        int size = 30;
        Page<String> emails;
        int successfulEmail = 0;
        int failedEmail = 0;
        List<String> failedAddress = new ArrayList<>();

        do {
            Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
            emails = messageRepository.findAllEmails(pageable);

            if (emails.isEmpty()) {
                break;
            }


            for (String email : emails.getContent()) {
                if (email == null || email.isBlank()) {
                    log.warn("No recipients  provided");
                    continue;
                }
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                    mimeMessageHelper.setFrom("admin");
                    mimeMessageHelper.setTo(email);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(htmlContent, true);
                    mailSender.send(mimeMessage);
                    successfulEmail++;
                } catch (MessagingException e) {
                    log.warn("Unable to send email {}", email, e);
                    failedEmail++;
                    failedAddress.add(email);
                }
            }
            page++;
        } while (emails.hasNext());
        return EmailSendingResult.builder()
                .failedAddress(failedAddress)
                .failedEmail(failedEmail)
                .successfulEmail(successfulEmail)
                .build();
    }
}

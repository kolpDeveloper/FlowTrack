package by.kolp.notificationservice.service;


import by.kolp.notificationservice.dto.EmailSendingResult;
import by.kolp.notificationservice.model.repository.MessageRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    @Value("${spring.mail.from}")
    private String from;

    private final MessageRepository messageRepository;
    private final JavaMailSender mailSender;

    public Page<String> findAllEmails(Pageable pageable) {
        return messageRepository.findAllEmails(PageRequest
                .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("email")
                        .ascending()));
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


            if(subject.isBlank() || subject.trim().isEmpty()) {
                throw new IllegalArgumentException("Subject cannot be empty");
            }

            if(htmlContent.isBlank() || htmlContent.trim().isEmpty()){
                throw new IllegalArgumentException("HtmlContent cannot be empty");
            }


            for (String email : emails.getContent()) {
                if (email == null || email.isBlank()) {
                    log.warn("No recipients  provided");
                    continue;
                }
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                    mimeMessageHelper.setFrom(from);
                    mimeMessageHelper.setTo(email);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(htmlContent, true);
                    mailSender.send(mimeMessage);
                    successfulEmail++;
                } catch (MessagingException e) {
                    log.warn("Unable to send email {}", email, e);
                    failedEmail++;
                    failedAddress.add(email);
                } catch (jakarta.mail.MessagingException e) {
                    throw new RuntimeException(e);
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

package by.kolp.notificationservice.controller;

import by.kolp.notificationservice.model.dto.AdminEmailRequest;
import by.kolp.notificationservice.model.dto.SubjectMessageDTO;
import by.kolp.notificationservice.service.MailSenderService;
import by.kolp.notificationservice.service.MessageService;
import by.kolp.notificationservice.service.UserClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMessageController {

    private final MailSenderService mailSenderService;
    private final MessageService messageService;
    private final UserClientService userClientService;

    @Transactional
    @RabbitListener(queues = "emailQueue")
    public ResponseEntity<Void> sendBulkHtmlAsync(@Payload SubjectMessageDTO email) {
        messageService.sendHtmlMessage(email.subject(), email.message());
        log.info("Email sent to :{}", email.subject());
        return ResponseEntity.accepted().build();
    }

    @Transactional
    @PostMapping("/api/admin/send/bulk")
    public void sendBulk(@RequestBody AdminEmailRequest request) {
        if(request.to() == null || request.to().isEmpty()){
            throw new IllegalArgumentException("Email to send cannot be empty");
        }

        Page<String> emails = userClientService.findAllEmails(Pageable.unpaged());

        mailSenderService.send(emails, request.subject(), request.body());
        log.info("Messages successfully sent!");
    }


}

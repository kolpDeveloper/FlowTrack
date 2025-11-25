package by.kolp.notificationservice.producer;

import by.kolp.notificationservice.dto.AdminEmailRequest;
import by.kolp.notificationservice.dto.SubjectMessageDTO;
import by.kolp.notificationservice.service.MailSenderService;
import by.kolp.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMessageController {

    private final MailSenderService mailSenderService;
    private final MessageService messageService;

    @RabbitListener(queues = "emailQueue")
    public ResponseEntity<Void> sendBulkHtmlAsync(@Payload SubjectMessageDTO email){
        messageService.sendHtmlMessage(email.subject(), email.message());
        log.info("Email sent to :{}", email.subject());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/api/admin/send/bulk")
    public ResponseEntity<String> sendBulk(@RequestBody AdminEmailRequest request)
    {
        Page<String> emails = messageService.findAllEmails(Pageable.unpaged());

        mailSenderService.send(emails, request.getSubject(), request.getMessage());
        log.info("Messages successfully sent!");
        return ResponseEntity.status(200).build();
    }


}

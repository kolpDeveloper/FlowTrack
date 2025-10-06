package by.kolp.myappproducer.controller;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import by.kolp.myappproducer.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminMessageController {

    private final MailSenderService mailSenderService;
    private final MessageService messageService;

    @PostMapping("/send/to/all")
    public ResponseEntity<String> sendToAll(@RequestBody AdminEmailRequest request) {

        List<String> emails = messageService.findAllEmails();
        if(emails.isEmpty()){
            log.info("No emails found!");
            return ResponseEntity.badRequest().build();
        }

        mailSenderService.send(emails, request.getSubject(), request.getMessage());
        log.info("Messages successfully sent!");
        return ResponseEntity.status(200).build();
    }
}

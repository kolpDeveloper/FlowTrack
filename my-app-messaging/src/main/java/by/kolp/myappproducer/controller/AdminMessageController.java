package by.kolp.myappproducer.controller;

import by.kolp.myappproducer.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import by.kolp.myappproducer.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/send/toall")
    public ResponseEntity<String> sendToAll(@RequestBody AdminEmailRequest request) {

        List<String> emails = messageService.getAllEmails();
        if(emails.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        mailSenderService.send(emails, request.getSubject(), request.getMessage());
        return ResponseEntity.status(200).build();
    }
}

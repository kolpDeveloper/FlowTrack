package by.kolp.myappproducer.producer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class RabbitMqController {

    private final MailSenderService mailSenderService;

    public RabbitMqController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody AdminEmailRequest email) {
        mailSenderService.sendToQueue(email);
        return ResponseEntity.ok("Email sent to queue");
    }
}

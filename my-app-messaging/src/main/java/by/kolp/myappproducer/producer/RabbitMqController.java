package by.kolp.myappproducer.producer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RabbitMqController {

    private final MailSenderService mailSenderService;

    @PostMapping("/admin/send")
    public ResponseEntity<String> send(@RequestBody AdminEmailRequest email) {
        mailSenderService.sendToQueue(email);
        return ResponseEntity.ok("Email sent to queue");
    }
}

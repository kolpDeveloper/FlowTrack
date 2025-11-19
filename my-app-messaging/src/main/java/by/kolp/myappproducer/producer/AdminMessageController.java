package by.kolp.myappproducer.producer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import by.kolp.myappproducer.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMessageController {

    private final MailSenderService mailSenderService;
    private final MessageService messageService;

    @PostMapping("/api/admin/send/bulk")
    public ResponseEntity<String> sendBulk(@RequestBody AdminEmailRequest request)
    {
        Page<String> emails = messageService.findAllEmails(Pageable.unpaged());

        mailSenderService.send(emails, request.getSubject(), request.getMessage());
        log.info("Messages successfully sent!");
        return ResponseEntity.status(200).build();
    }


}

package by.kolp.myappproducer.producer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.dto.SubjectMessageDTO;
import by.kolp.myappproducer.service.MailSenderService;
import by.kolp.myappproducer.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMessageController {

    private final MailSenderService mailSenderService;
    private final MessageService messageService;

    @PostMapping("/admin/send/bulk")
    public ResponseEntity<String> sendBulk(@RequestBody AdminEmailRequest request,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "50") int size)
    {
        Page<String> emails = messageService.findAllEmails(page, size);

        mailSenderService.send(emails, request.getSubject(), request.getMessage());
        log.info("Messages successfully sent!");
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/admin/send/bulk/html")
    public void sendHtml(@RequestBody SubjectMessageDTO request){
        mailSenderService.sendToQueue(request);
    }


}

package by.kolp.myappproducer.producer;

import by.kolp.myappproducer.dto.SubjectMessageDTO;
import by.kolp.myappproducer.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMessageController {

    private final MailSenderService mailSenderService;

    @PostMapping("/admin/send/bulk")
    public void sendHtml(@RequestBody SubjectMessageDTO request){
        mailSenderService.sendToQueue(request);
    }
}

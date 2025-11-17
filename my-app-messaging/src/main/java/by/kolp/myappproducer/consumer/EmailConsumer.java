package by.kolp.myappproducer.consumer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.dto.SubjectMessageDTO;
import by.kolp.myappproducer.service.MailSenderService;
import by.kolp.myappproducer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    private final MailSenderService mailSender;
    private final MessageService messageService;

    public EmailConsumer(MailSenderService mailSender, MessageService messageService) {
        this.mailSender = mailSender;
        this.messageService = messageService;
    }

    /*@RabbitListener(queues = "emailQueue")
    public void receive(@Payload AdminEmailRequest email) {
        log.info("Email received!");
        mailSender.send(email.getTo(), email.getSubject(), email.getMessage());
    }*/

    @RabbitListener(queues = "emailQueue")
    public void sendBulkHtmlAsync(@Payload SubjectMessageDTO email){
        messageService.sendHtmlMessage(email.subject(), email.message());
        log.info("Email sent to :{}", email.subject());
    }
}

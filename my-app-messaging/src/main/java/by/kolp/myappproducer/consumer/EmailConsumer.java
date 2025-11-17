package by.kolp.myappproducer.consumer;

import by.kolp.myappproducer.dto.SubjectMessageDTO;
import by.kolp.myappproducer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    private final MessageService messageService;

    public EmailConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    @RabbitListener(queues = "emailQueue")
    public ResponseEntity<Void> sendBulkHtmlAsync(@Payload SubjectMessageDTO email){
        messageService.sendHtmlMessage(email.subject(), email.message());
        log.info("Email sent to :{}", email.subject());
        return ResponseEntity.accepted().build();
    }
}

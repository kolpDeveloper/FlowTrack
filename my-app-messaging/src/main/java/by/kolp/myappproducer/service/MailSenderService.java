package by.kolp.myappproducer.service;


import by.kolp.myappproducer.dto.SubjectMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailSenderService {

    private final RabbitTemplate rabbitTemplate;

    public MailSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(SubjectMessageDTO email) {
        rabbitTemplate.convertAndSend("emailQueue", email);
        log.info("Email sent to queue");
    }
}

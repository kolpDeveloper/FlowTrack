package by.kolp.myappproducer.consumer;

import by.kolp.myappproducer.dto.AdminEmailRequest;
import by.kolp.myappproducer.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    private final MailSenderService mailSender;

    public EmailConsumer(MailSenderService mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "emailQueue")
    public void receive(AdminEmailRequest email) {
        log.info("Email received!");
        mailSender.send(email.getTo(), email.getSubject(), email.getMessage());
    }



}

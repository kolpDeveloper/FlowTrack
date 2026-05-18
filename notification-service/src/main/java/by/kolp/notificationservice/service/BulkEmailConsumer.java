package by.kolp.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BulkEmailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener
    public void send(Page<String> emails, String subject, String text) {

    }

}

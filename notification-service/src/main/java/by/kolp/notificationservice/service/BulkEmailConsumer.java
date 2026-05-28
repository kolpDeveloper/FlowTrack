package by.kolp.notificationservice.service;

import by.kolp.notificationservice.model.dto.BulkEmailRequest;
import by.kolp.notificationservice.model.dto.EmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BulkEmailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener
    public void handleBulkEmailRequest(BulkEmailRequest bulkEmailRequest) {

        log.info("Bulk email request received: {}, recipients, subject : {}",
                bulkEmailRequest.recipients().getSize(),
                bulkEmailRequest.subject());

        EmailResult result = mailSenderService.send(bulkEmailRequest.recipients(), bulkEmailRequest.subject(), bulkEmailRequest.body());

        log.info("Bulk email completed result: {}, failed, success : {}",
                result.failed(), result.success());
    }
}

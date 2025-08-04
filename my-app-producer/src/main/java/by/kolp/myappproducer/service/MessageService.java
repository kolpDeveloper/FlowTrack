package by.kolp.myappproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.queue_with_delay.name}")
    private String queueWithDelay;

    private final AmqpTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendWithDelay(String message) {
        amqpTemplate.convertAndSend(queueWithDelay, message);
    }

}

package by.kolp.myappproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.queue_with_delay.name}")
    private String queueWithDelay;

    private final RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    public void sendWithDelay(String message) {
        rabbitTemplate.convertAndSend(queueWithDelay, message);
    }

    public void sendToAllUsers(String message) {

    }

}

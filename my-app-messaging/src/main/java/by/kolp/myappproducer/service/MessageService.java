package by.kolp.myappproducer.service;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.queue_with_delay.name}")
    private String queueWithDelay;

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;


    public void sendWithDelay(String message) {
        rabbitTemplate.convertAndSend(queueWithDelay, message);
    }

    public List<String> getAllEmails(){
        return userRepository.findAll()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}

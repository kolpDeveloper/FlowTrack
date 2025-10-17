package by.kolp.myappproducer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.json.queue.name}")
    private String jsonQueue;


    @Bean
    public Queue queue() {
        return new Queue(queue, false);
    }

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue, false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue", false);
    }
}

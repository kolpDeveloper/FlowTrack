package by.kolp.myappproducer.config;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
    public MessageConverter convertMessage(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }


    @Bean
    public Queue queue() {
        return new Queue(queue, false, false, false);
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

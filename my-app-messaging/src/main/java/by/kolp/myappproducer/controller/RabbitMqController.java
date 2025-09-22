package by.kolp.myappproducer.controller;

import by.kolp.myappproducer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/message")
public class RabbitMqController {

    private final MessageService messageService;

    public RabbitMqController(MessageService messageService) {
        this.messageService = messageService;
    }

    
    @PostMapping("/send/delay")
    public ResponseEntity<?> sendWithDelay(@RequestBody String message) {
        if(message.isBlank()){
            return ResponseEntity.badRequest().build();
        }
        messageService.sendWithDelay(message);
        log.info("Sent message with delay: {}", message);
        return ResponseEntity.status(200).build();
    }


}

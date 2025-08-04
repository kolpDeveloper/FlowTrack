package by.kolp.myappproducer.controller;

import by.kolp.myappproducer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

    private final MessageService messageService;

    @Autowired
    public RabbitMqController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody String message) {
        if(message.isBlank()){
            return ResponseEntity.badRequest().build();
        }
        messageService.send(message);
        return ResponseEntity.status(200).build();
    }

    
    @PostMapping("/send/delay")
    public ResponseEntity<?> sendWithDelay(@RequestBody String message) {
        if(message.isBlank()){
            return ResponseEntity.badRequest().build();
        }

        messageService.sendWithDelay(message);
        return ResponseEntity.status(200).build();
    }
}

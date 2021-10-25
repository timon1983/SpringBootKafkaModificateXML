package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.kafka.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    Producer producer;

    public ProducerController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("publish")
    public String sendMessage(@RequestParam String message){
        this.producer.sendMessage(message);
        return "Published successfully";
    }
}

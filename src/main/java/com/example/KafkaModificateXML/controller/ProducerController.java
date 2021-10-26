package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.kafka.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * тестовый класс для эмуляции получения входящего сообщения
 */
@RestController
public class ProducerController {

    Producer producer;

    public ProducerController(Producer producer) {
        this.producer = producer;
    }

    /**
     * отправка входящего сообщения в кафку
     *
     * @param message
     * @return
     */
    @PostMapping("publish")
    public String sendMessage(@RequestParam String message) {
        this.producer.sendMessage(message);
        return "Published successfully";
    }
}

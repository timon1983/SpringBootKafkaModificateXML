package com.example.KafkaModificateXML.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${producer.topic}")
    private String TOPIC;
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        System.out.println("Producer send message " + message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}

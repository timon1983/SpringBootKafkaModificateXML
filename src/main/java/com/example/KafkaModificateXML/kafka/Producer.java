package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private TopicService topicService;
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate kafkaTemplate, TopicService topicService) {

        this.kafkaTemplate = kafkaTemplate;
        this.topicService = topicService;
    }

    public void sendMessage(String message) {
        String TOPIC = topicService.findAll().getSendTopic();
        this.kafkaTemplate.send(TOPIC, message);
    }
}

package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.service.MessageService;
import com.example.KafkaModificateXML.service.TopicService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final ModificationXML modificationXML;
    private MessageService messageService;
    private TopicService topicService;
    private String topic;

    @Autowired
    public Consumer(ModificationXML modificationXML, MessageService messageService, TopicService topicService) {
        this.modificationXML = modificationXML;
        this.messageService = messageService;
        this.topicService = topicService;
    }

    @KafkaListener(topics = "murex-fx-swap-out", groupId = "group_id")
    public void consume(String message) {

        modificationXML.modificationAndSendToProducerXML(message);
    }
}

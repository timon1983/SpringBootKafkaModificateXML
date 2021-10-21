package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final ModificationXML modificationXML;

    @Autowired
    public Consumer(ModificationXML modificationXML) {
        this.modificationXML = modificationXML;
    }

    @KafkaListener(topics = "murex-fx-swap-out", groupId = "group_id")
    public void consume(String message) {
        modificationXML.modificationAndSendToProducerXML(message);

    }
}

package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.model.DataXML;
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
    public Producer( KafkaTemplate kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void ptintData(DataXML dataXML){
        System.out.println(dataXML);
    }

    public void modificationXML(String tagName , String attribute , String valueOfAttribute){

    }

    public void sendMessage(String message){
        System.out.println("Producer send message " + message);
        this.kafkaTemplate.send(TOPIC,message);
    }
}

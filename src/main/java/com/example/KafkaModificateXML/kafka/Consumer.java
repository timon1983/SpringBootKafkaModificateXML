package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.service.TopicService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final static Logger log = LogManager.getLogger(Consumer.class);

    private final ModificationXML modificationXML;
    private DataService dataService;

    @Autowired
    public Consumer(ModificationXML modificationXML, DataService dataService) {
        this.modificationXML = modificationXML;
        this.dataService = dataService;
    }

    @KafkaListener(topics = "#{topicService.findAll().getReadTopic()}", groupId = "group_id")
    public void consume(String message) {
        log.info(message);
        DataXmlDTO dataXmlDTO = dataService.findAllByMaxVersion();
        modificationXML.workWithDataXML(dataXmlDTO);
        modificationXML.modificationAndSendToProducerXML(message);
    }
}

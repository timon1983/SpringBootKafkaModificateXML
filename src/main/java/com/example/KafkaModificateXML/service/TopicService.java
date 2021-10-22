package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.TopicEntity;
import com.example.KafkaModificateXML.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void save(DataXmlDTO dataXmlDTO) {
        if (dataXmlDTO.getReadTopic() != null && dataXmlDTO.getSendTopic() != null) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setReadTopic(dataXmlDTO.getReadTopic());
            topicEntity.setSendTopic(dataXmlDTO.getSendTopic());
            topicRepository.deleteAll();
            topicRepository.save(topicEntity);
        }
    }

    public List<TopicEntity> findAll(){
        return topicRepository.findAll();
    }
}

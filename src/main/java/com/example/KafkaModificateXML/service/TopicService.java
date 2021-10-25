package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.MessageDTO;
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

    public void save(MessageDTO messageDTO) {
        if (messageDTO.getReadTopic() != null && messageDTO.getSendTopic() != null) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setReadTopic(messageDTO.getReadTopic());
            topicEntity.setSendTopic(messageDTO.getSendTopic());
            topicRepository.deleteAll();
            topicRepository.save(topicEntity);
        }
    }

    public MessageDTO findAll() {
        return getMessageDTO(topicRepository.findAll());
    }

    public MessageDTO getMessageDTO(List<TopicEntity> topicEntities) {
        if(!(topicEntities.isEmpty())){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setReadTopic(topicEntities.get(0).getReadTopic());
        messageDTO.setSendTopic(topicEntities.get(0).getSendTopic());
        return messageDTO;
        } else {
            return new MessageDTO();
        }
    }
}

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

    /**
     * запись топиков в БД
     *
     * @param messageDTO
     */
    public void save(MessageDTO messageDTO) {
        if (messageDTO.getReadTopic() != null && messageDTO.getSendTopic() != null) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setSendTopic(messageDTO.getSendTopic());
            topicEntity.setReadTopic(messageDTO.getReadTopic());
            topicRepository.deleteAll();
            topicRepository.save(topicEntity);
        }
    }

    /**
     * получение всех топиков
     *
     * @return
     */
    public MessageDTO findAll() {
       return topicRepository
               .findAll()
               .stream()
               .map(x -> MessageDTO
                       .builder()
                       .sendTopic(x.getSendTopic())
                       .readTopic(x.getReadTopic())
                       .build())
               .findFirst()
               .orElse(MessageDTO
                       .builder()
                       .build());
    }
}

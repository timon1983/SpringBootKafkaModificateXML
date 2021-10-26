package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.MessageDTO;
import com.example.KafkaModificateXML.model.MessageEntity;
import com.example.KafkaModificateXML.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(MessageDTO messageDTO) {
        if (messageDTO.getReadTopic() != null && messageDTO.getSendTopic() != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setInMessage(messageDTO.getIncomeMessage());
            messageEntity.setOutMessage(messageDTO.getOutgoingMessage());
            messageRepository.deleteAll();
            messageRepository.save(messageEntity);
        }
    }

    public MessageDTO findAll() {
        List<MessageEntity> messageEntities = messageRepository.findAll();
        if (!(messageEntities.isEmpty())) {
            return getMessageDTO(messageEntities);
        } else {
            return new MessageDTO();
        }
    }

    public MessageDTO getMessageDTO(List<MessageEntity> messageEntities) {
        if (!(messageEntities.isEmpty())) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setIncomeMessage(messageEntities.get(0).getInMessage());
            messageDTO.setOutgoingMessage(messageEntities.get(0).getOutMessage());
            return messageDTO;
        } else {
            return new MessageDTO();
        }
    }
}

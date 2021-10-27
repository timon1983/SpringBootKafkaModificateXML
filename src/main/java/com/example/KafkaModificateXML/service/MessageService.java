package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.MessageDTO;
import com.example.KafkaModificateXML.model.MessageEntity;
import com.example.KafkaModificateXML.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * сохранение сообщений в БД
     *
     * @param messageDTO
     */
    public void save(MessageDTO messageDTO) {
        if (messageDTO.getReadTopic() != null && messageDTO.getSendTopic() != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setInMessage(messageDTO.getIncomeMessage());
            messageEntity.setOutMessage(messageDTO.getOutgoingMessage());
            messageRepository.deleteAll();
            messageRepository.save(messageEntity);
        }
    }

    /**
     * получение всех сообщений из БД
     *
     * @return
     */
    public MessageDTO findAll() {
        return messageRepository.findAll()
                .stream()
                .map(x -> MessageDTO.builder()
                        .incomeMessage(x.getInMessage())
                        .outgoingMessage(x.getOutMessage())
                        .build())
                .findFirst()
                .orElse(MessageDTO
                        .builder()
                        .build());
    }
}


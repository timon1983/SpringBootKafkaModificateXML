package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.MessageDTO;
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

    public void save(MessageDTO messageDTO){

    }
}

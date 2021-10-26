package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.MessageDTO;
import com.example.KafkaModificateXML.service.MessageService;
import com.example.KafkaModificateXML.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

/**
 * Контроллер для задания значений шаблона исходящего сообщения и топиков
 */
@Controller
@RequestMapping("UI")
public class MessageController {

    private MessageService messageService;
    private TopicService topicService;

    @Autowired
    public MessageController(MessageService messageService, TopicService topicService) {
        this.messageService = messageService;
        this.topicService = topicService;
    }

    /**
     * вывод сообщений и топиков
     *
     * @param messageDTO
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("/message")
    public String getMessages(MessageDTO messageDTO) throws InvocationTargetException, IllegalAccessException {
        MessageDTO message = messageService.findAll();
        MessageDTO topic = topicService.findAll();
        message.setReadTopic(topic.getReadTopic());
        message.setSendTopic(topic.getSendTopic());
        copyProperties(messageDTO, message);
        return "message-insert-form";
    }

    /**
     * задание новых сообщений и топиков
     *
     * @param messageDTO
     * @return
     */
    @PostMapping("/message")
    public String addMessage(MessageDTO messageDTO) {
        messageService.save(messageDTO);
        topicService.save(messageDTO);
        return "redirect:/UI";
    }
}

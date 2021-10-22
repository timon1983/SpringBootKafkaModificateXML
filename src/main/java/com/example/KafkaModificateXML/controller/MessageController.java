package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.MessageDTO;
import com.example.KafkaModificateXML.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("UI")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/message")
    public String getMessages(MessageDTO messageDTO){
        return "/message-insert-form";
    }

    @PostMapping("/message")
    public String addMessage(MessageDTO messageDTO){
        messageService.save(messageDTO);
        return "redirect:/message-insert-form";
    }
}

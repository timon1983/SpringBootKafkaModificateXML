package com.example.KafkaModificateXML.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {

    private String incomeMessage;
    private String outgoingMessage;
    private String readTopic;
    private String sendTopic;

    public MessageDTO(String incomeMessage, String outgoingMessage, String readTopic, String sendTopic) {
        this.incomeMessage = incomeMessage;
        this.outgoingMessage = outgoingMessage;
        this.readTopic = readTopic;
        this.sendTopic = sendTopic;
    }
}

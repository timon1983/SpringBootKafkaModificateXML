package com.example.KafkaModificateXML.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private String incomeMessage;
    private String outgoingMessage;
    private String readTopic;
    private String sendTopic;
}

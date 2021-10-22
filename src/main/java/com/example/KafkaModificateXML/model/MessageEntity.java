package com.example.KafkaModificateXML.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "in_message", length = 45)
    private String inMessage;
    @Column(name = "out_message", length = 45)
    private String outMessage;

    public MessageEntity() {
    }
}

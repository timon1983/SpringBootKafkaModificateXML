package com.example.KafkaModificateXML.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "topics")
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "read_topic", length = 45)
    private String readTopic;
    @Column(name = "send_topic", length = 45)
    private String sendTopic;

    public TopicEntity() {
    }
}

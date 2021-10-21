package com.example.KafkaModificateXML.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "data")
public class DataDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "incomefield1" , length = 45)
    private String incomeField1;
    @Column(name = "incomefield2" , length = 45)
    private String incomeField2;
    @Column(name = "incomefield3" , length = 45)
    private String incomeField3;
    @Column(name = "incomefield4" , length = 45)
    private String incomeField4;
    @Column(name = "outgoingfield1" , length = 45)
    private String outgoingField1;
    @Column(name = "outgoingfield2" , length = 45)
    private String outgoingField2;
    @Column(name = "outgoingfield3" , length = 45)
    private String outgoingField3;
    @Column(name = "outgoingfield4" , length = 45)
    private String outgoingField4;



    @Override
    public String toString() {
        return "DataDTO{" +
                "incomeField1='" + incomeField1 + '\'' +
                ", incomeField2='" + incomeField2 + '\'' +
                ", incomeField3='" + incomeField3 + '\'' +
                ", incomeField4='" + incomeField4 + '\'' +
                ", outgoingField1='" + outgoingField1 + '\'' +
                ", outgoingField2='" + outgoingField2 + '\'' +
                ", outgoingField3='" + outgoingField3 + '\'' +
                ", outgoingField4='" + outgoingField4 + '\'' +
                '}';
    }
}

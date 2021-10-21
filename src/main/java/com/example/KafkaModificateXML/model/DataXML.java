package com.example.KafkaModificateXML.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "data")
public class DataXML {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "type" , length = 45)
    @Enumerated(EnumType.STRING)
    private TypeXML type;
    @Column(name = "in_value" , length = 45)
    private String inValue;
    @Column(name = "out_value" , length = 45)
    private String outValue;

    public DataXML() {
    }

    public DataXML(TypeXML type, String inValue, String outValue) {
        this.type = type;
        this.inValue = inValue;
        this.outValue = outValue;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", type=" + type +
                ", inValue='" + inValue + '\'' +
                ", outValue='" + outValue + '\'' +
                '}';
    }
}

package com.example.KafkaModificateXML.model;

import lombok.Data;

@Data
public class DataDTO {

    private String incomeField1;
    private String incomeField2;
    private String incomeField3;
    private String incomeField4;
    private String outgoingField1;
    private String outgoingField2;
    private String outgoingField3;
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

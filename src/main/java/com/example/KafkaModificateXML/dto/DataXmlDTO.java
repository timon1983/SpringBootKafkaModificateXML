package com.example.KafkaModificateXML.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DataXmlDTO {

    private String type;
    private String in1;
    private String in2;
    private String in3;
    private String in4;
    private String out1;
    private String out2;
    private String out3;
    private String out4;
    private Integer version;
    private String readTopic;
    private String sendTopic;

    public DataXmlDTO(String type, String in1, String in2, String in3, String in4, String out1, String out2, String out3, String out4, Integer version, String readTopic, String sendTopic) {
        this.type = type;
        this.in1 = in1;
        this.in2 = in2;
        this.in3 = in3;
        this.in4 = in4;
        this.out1 = out1;
        this.out2 = out2;
        this.out3 = out3;
        this.out4 = out4;
        this.version = version;
        this.readTopic = readTopic;
        this.sendTopic = sendTopic;
    }
}

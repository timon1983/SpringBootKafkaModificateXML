package com.example.KafkaModificateXML.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
    private int version;
    private String readTopic;
    private String sendTopic;

}

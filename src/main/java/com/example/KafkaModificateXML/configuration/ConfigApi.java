package com.example.KafkaModificateXML.configuration;

import com.example.KafkaModificateXML.service.ModificationXML;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigApi {

    @Bean
    public ModificationXML getModificationXML(){
        return new ModificationXML();
    }
}

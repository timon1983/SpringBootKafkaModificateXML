package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.model.DataDTO;
import com.example.KafkaModificateXML.service.ModificationXML;
import com.example.KafkaModificateXML.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class KafkaController {

    private Producer producer;
    private ModificationXML modificationXML;

    @Autowired
    public KafkaController(Producer producer, ModificationXML modificationXML) {
        this.producer = producer;
        this.modificationXML = modificationXML;
    }

    @GetMapping("/UI")
    public String getUIModification(DataDTO dataDTO , Model model){
        List<String> listFieldsNameOutgoing = modificationXML.getListOfFieldNameOutgoingXML();
        model.addAttribute("listFieldsNameOutgoing" , listFieldsNameOutgoing);
        return "modification-form";
    }

    @PostMapping("/UI")
    public String modificationXML(DataDTO dataDTO){
       producer.ptintData(dataDTO);
        return "redirect:/UI";
    }
}

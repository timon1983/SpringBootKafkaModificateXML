package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.DataXML;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import com.example.KafkaModificateXML.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.example.KafkaModificateXML.model.TypeXML.*;
import static org.apache.commons.beanutils.BeanUtils.copyProperties;

@Controller
public class KafkaController {


    private Producer producer;
    private ModificationXML modificationXML;
    private DataService dataService;
    private String type = "CALIPSOPI";

    @Autowired
    public KafkaController(Producer producer, ModificationXML modificationXML, DataService dataService) {
        this.producer = producer;
        this.modificationXML = modificationXML;
        this.dataService = dataService;
    }

    @GetMapping("/UI")
    public String getUIModification(DataXmlDTO dataXmlDTO, Model model) {

       // System.out.println(dataXmlDTO);
        List<String> listFieldsNameOutgoing = modificationXML.getListOfFieldNameOutgoingXML();
        model.addAttribute("listFieldsNameOutgoing" , listFieldsNameOutgoing);
        return "modification-form";
    }

    @GetMapping("UI/CALIPSOPI")
    public String getMurexData(DataXmlDTO dataXmlDTO){
        dataXmlDTO = dataService.findAllByType(CALIPSOPI);
        System.out.println(dataXmlDTO);
        return "modification-form";
    }

    @PostMapping("/UI")
    public String modificationXML(DataXmlDTO dataXmlDTO){
         type = dataXmlDTO.getType();
       dataService.save(dataXmlDTO);
        //producer.ptintData(dataXML);
        return "redirect:/UI";
    }
}
//copyProperties(dataXmlDTO, dataService.findAll().get(0));
// dataService.getModificationFields(dataXmlDTO);
package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import com.example.KafkaModificateXML.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
        List<String> listFieldsNameOutgoing = modificationXML.getListOfFieldNameOutgoingXML();
        model.addAttribute("listFieldsNameOutgoing" , listFieldsNameOutgoing);
        return "modification-form";
    }

    @GetMapping("UI/{type}/{version}")
    public String getMurexData(@PathVariable String type, @PathVariable int version, DataXmlDTO dataXmlDTO)
            throws InvocationTargetException, IllegalAccessException {
        TypeXML typeXML = TypeXML.valueOf(type);
        copyProperties(dataXmlDTO, dataService.findAllByType(typeXML, version));
        return "modification-form";
    }

    @PostMapping("/UI")
    public String modificationXML(DataXmlDTO dataXmlDTO){
         type = dataXmlDTO.getType();
         DataXmlDTO  dataXmlDTO1 =  dataService.save(dataXmlDTO);
         return "redirect:/UI/" + dataXmlDTO1.getType() + "/" + dataXmlDTO1.getVersion();
    }
}

package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.model.DataDTO;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.service.ModificationXML;
import com.example.KafkaModificateXML.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import static org.apache.commons.beanutils.BeanUtils.copyProperties;

@Controller
public class KafkaController {

    private Producer producer;
    private ModificationXML modificationXML;
    private DataService dataService;

    @Autowired
    public KafkaController(Producer producer, ModificationXML modificationXML, DataService dataService) {
        this.producer = producer;
        this.modificationXML = modificationXML;
        this.dataService = dataService;
    }

    @GetMapping("/UI")
    public String getUIModification(DataDTO dataDTO , Model model) throws InvocationTargetException, IllegalAccessException {
        copyProperties(dataDTO, dataService.findAll().get(0));
        List<String> listFieldsNameOutgoing = modificationXML.getListOfFieldNameOutgoingXML();
        model.addAttribute("listFieldsNameOutgoing" , listFieldsNameOutgoing);
        return "modification-form";
    }

    @PostMapping("/UI")
    public String modificationXML(DataDTO dataDTO){
        dataService.save(dataDTO);
       producer.ptintData(dataDTO);
        return "redirect:/UI";
    }
}

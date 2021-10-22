package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.TopicEntity;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.service.TopicService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

@Controller
@RequestMapping("UI")
public class KafkaController {

    private ModificationXML modificationXML;
    private DataService dataService;
    private TopicService topicService;

    @Autowired
    public KafkaController(ModificationXML modificationXML, DataService dataService, TopicService topicService) {
        this.modificationXML = modificationXML;
        this.dataService = dataService;
        this.topicService = topicService;
    }

    @GetMapping("")
    public String getUIModification(DataXmlDTO dataXmlDTO, Model model) {
        List<String> listFieldsNameOutgoing = modificationXML.getListOfFieldNameOutgoingXML();
        model.addAttribute("listFieldsNameOutgoing", listFieldsNameOutgoing);
        return "modification-form";
    }

    @GetMapping("/{type}/{version}")
    public String getMurexData(@PathVariable String type, @PathVariable int version, DataXmlDTO dataXmlDTO)
            throws InvocationTargetException, IllegalAccessException {
        TopicEntity topic = topicService.findAll().get(0);
        TypeXML typeXML = TypeXML.valueOf(type);
        copyProperties(dataXmlDTO, dataService.findAllByTypeAndVersion(typeXML, version));
        dataXmlDTO.setReadTopic(topic.getReadTopic());
        dataXmlDTO.setSendTopic(topic.getSendTopic());
        return "modification-form";
    }

    @PostMapping("")
    public String modificationXML(DataXmlDTO dataXmlDTO) {
        DataXmlDTO dataXmlDTO1 = dataService.save(dataXmlDTO);
        topicService.save(dataXmlDTO);
        return "redirect:/UI/" + dataXmlDTO1.getType() + "/" + dataXmlDTO1.getVersion();
    }
}

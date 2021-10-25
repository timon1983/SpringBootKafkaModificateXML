package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.service.MessageService;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

@Controller
@RequestMapping("UI")
public class KafkaController {

    private ModificationXML modificationXML;
    private DataService dataService;
    private MessageService messageService;

    @Autowired
    public KafkaController(ModificationXML modificationXML, DataService dataService, MessageService messageService) {
        this.modificationXML = modificationXML;
        this.dataService = dataService;
        this.messageService = messageService;
    }

    @GetMapping("")
    public String getUIModification(DataXmlDTO dataXmlDTO, Model model)
            throws InvocationTargetException, IllegalAccessException {
        copyProperties(dataXmlDTO, dataService.findAllByMaxVersion());
        List<String> listFieldsNameOutgoing = modificationXML
                .getListOfFieldNameOutgoingXML(messageService
                        .findAll()
                        .getOutgoingMessage());
        model.addAttribute("listFieldsNameOutgoing", listFieldsNameOutgoing);
        return "modification-form";
    }

    @GetMapping("/{type}/{version}")
    public String getMurexData(@PathVariable String type, @PathVariable int version, DataXmlDTO dataXmlDTO)
            throws InvocationTargetException, IllegalAccessException {
        TypeXML typeXML = TypeXML.valueOf(type);
        copyProperties(dataXmlDTO, dataService.findAllByTypeAndVersion(typeXML, version));
       // modificationXML.workWithDataXML(dataXmlDTO);
        return "modification-form";
    }

    @PostMapping("")
    public String modificationXML(DataXmlDTO dataXmlDTO) {
        DataXmlDTO dataXmlDTO1 = dataService.save(dataXmlDTO);
        return "redirect:/UI/" + dataXmlDTO1.getType() + "/" + dataXmlDTO1.getVersion();
    }
}

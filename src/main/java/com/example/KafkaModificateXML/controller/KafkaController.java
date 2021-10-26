package com.example.KafkaModificateXML.controller;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.service.DataService;
import com.example.KafkaModificateXML.service.MessageService;
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

/**
 * Контроллер для натсройки маппинга значиний входящего и исходящего сообщения
 */
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

    /**
     * вывод значений последней версии маппинга
     *
     * @param dataXmlDTO
     * @param model
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("")
    public String getUIModification(DataXmlDTO dataXmlDTO, Model model)
            throws InvocationTargetException, IllegalAccessException {
        copyProperties(dataXmlDTO, dataService.findAllByMaxVersion());
        List<String> listFieldsNameOutgoing = modificationXML
                .getListOfFieldNameXML(messageService
                        .findAll()
                        .getOutgoingMessage());
        model.addAttribute("listFieldsNameOutgoing", listFieldsNameOutgoing);
        return "modification-form";
    }

    /**
     * вывод значений маппинга по типу и версии
     *
     * @param type
     * @param version
     * @param dataXmlDTO
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("/{type}/{version}")
    public String getMurexData(@PathVariable String type, @PathVariable int version, DataXmlDTO dataXmlDTO)
            throws InvocationTargetException, IllegalAccessException {
        TypeXML typeXML = TypeXML.valueOf(type);
        copyProperties(dataXmlDTO, dataService.findAllByTypeAndVersion(typeXML, version));
        return "modification-form";
    }

    /**
     * ввод новых значений маппинга
     *
     * @param dataXmlDTO
     * @return
     */
    @PostMapping("")
    public String modificationXML(DataXmlDTO dataXmlDTO) {
        DataXmlDTO dataXmlDTO1 = dataService.save(dataXmlDTO);
        return "redirect:/UI/" + dataXmlDTO1.getType() + "/" + dataXmlDTO1.getVersion();
    }
}

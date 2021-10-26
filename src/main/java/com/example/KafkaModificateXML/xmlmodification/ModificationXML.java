package com.example.KafkaModificateXML.xmlmodification;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.kafka.Producer;
import com.example.KafkaModificateXML.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import ru.trd.msk.svn.xsd.fixml.FIXML;
import ru.trd.msk.svn.xsd.fixml.TradeCaptureReportMessageT;

import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * класс для преобразования xml в объект и обратно
 */
@Component
public class ModificationXML {

    private final static Logger log = LogManager.getLogger(ModificationXML.class);

    private Producer producer;
    private FIXML incomingMessage;
    private FIXML outgoingMessage;
    private List<String> fieldsIncomeForWork;
    private List<String> fieldsOutgoingForWork;
    private ChangeObjectValue changeObjectValue;
    private MessageService messageService;

    @Autowired
    public ModificationXML(Producer producer, ChangeObjectValue changeObjectValue, MessageService messageService) {
        this.producer = producer;
        this.changeObjectValue = changeObjectValue;
        this.messageService = messageService;
    }

    public ModificationXML() {
    }

    /**
     * демаршализация входящего xml файла в объект FIXML с игнорированием пространства имен ,
     * извлечение целевых значений, демаршализация xml файла шаблона для отправки ,
     * установка значений , маршализация в строку , отправка сообщения
     *
     * @param xml
     */
    public void modificationAndSendToProducerXML(String xml) {

        unmarshalIncomingMessage(xml);

        unmarshalOutgoingMessage(messageService.findAll().getOutgoingMessage());

        FIXML changedOutMessage = (FIXML) changeObjectValue
                .changeXmlMessage(incomingMessage, outgoingMessage, fieldsIncomeForWork, fieldsOutgoingForWork);

        //producer.sendMessage(marshalToStringOutgoingXML(changedOutMessage));
        log.info(marshalToStringOutgoingXML(changedOutMessage));
    }

    /**
     * демаршализация исходящего xml в объект FIXML
     */
    public FIXML unmarshalOutgoingMessage(String outXML) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(FIXML.class);
            Unmarshaller unmarshaller1 = jaxbContext.createUnmarshaller();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader2 = factory.createXMLStreamReader(getStringReader(outXML));
            outgoingMessage = unmarshaller1.unmarshal(xmlStreamReader2, FIXML.class).getValue();
        } catch (JAXBException | XMLStreamException e) {
            e.printStackTrace();
        }
        return outgoingMessage;
    }

    /**
     * демаршализация входящего xml файла в объект FIXML с игнорированием пространства имен ,
     * извлечение целевых значений
     *
     * @param xml
     */
    public FIXML unmarshalIncomingMessage(String xml) {
        JAXBContext jaxbContext;
        List<String> fieldsValueForInsert;
        try {
            jaxbContext = JAXBContext.newInstance(FIXML.class);
            Unmarshaller unmarshaller1 = jaxbContext.createUnmarshaller();
            XMLFilter filter = new NamespaceFilter();
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            filter.setParent(xr);
            UnmarshallerHandler unmarshallerHandler = unmarshaller1.getUnmarshallerHandler();
            filter.setContentHandler(unmarshallerHandler);
            InputSource is = new InputSource();
            is.setCharacterStream(getStringReader(xml));
            filter.parse(is);
            incomingMessage = (FIXML) unmarshallerHandler.getResult();
        } catch (IOException | JAXBException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return incomingMessage;
    }

    /**
     * маршализация и преобразование в строку
     *
     * @param changedOutMessage
     * @return
     */
    public String marshalToStringOutgoingXML(FIXML changedOutMessage) {
        JAXBContext jaxbContext;
        String result = null;
        try {
            jaxbContext = JAXBContext.newInstance(FIXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(changedOutMessage, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * получение объекто StringReader для чтения XML ввиде строки
     *
     * @param text
     * @return
     */
    public StringReader getStringReader(String text) {
        return new StringReader(text);
    }

    /**
     * создание списков полей входящего и исходящего XML для модификации их значений
     *
     * @param dataXmlDTO
     */
    public void workWithDataXML(DataXmlDTO dataXmlDTO) {
        fieldsIncomeForWork = List.of(dataXmlDTO.getIn1(), dataXmlDTO.getIn2(), dataXmlDTO.getIn3(), dataXmlDTO.getIn4());
        fieldsOutgoingForWork = List.of(dataXmlDTO.getOut1(), dataXmlDTO.getOut2(), dataXmlDTO.getOut3(), dataXmlDTO.getOut4());
    }

    /**
     * получение списка всех полей  XML
     *
     * @return
     */
    public List<String> getListOfFieldNameXML(String outXML) {
        FIXML fixml = unmarshalOutgoingMessage(outXML);
        Field[] fieldsOutgoingMessage = ((TradeCaptureReportMessageT) fixml
                .getBatch()
                .get(0).getMessage()
                .get(0)
                .getValue())
                .getClass()
                .getDeclaredFields();
        List<String> listFieldsNameOutgoing = Arrays.stream(fieldsOutgoingMessage)
                .map(Field::getName)
                .collect(Collectors.toList());
        return listFieldsNameOutgoing;
    }
}



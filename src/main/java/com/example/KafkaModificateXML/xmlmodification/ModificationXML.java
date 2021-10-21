package com.example.KafkaModificateXML.xmlmodification;

import com.example.KafkaModificateXML.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Component
public class ModificationXML {


    private Producer producer;
    @Value("${consumer.tagName}")
    private String tagName;
    @Value("${consumer.attribute}")
    private String attribute;
    @Value("${answer.xml}")
    private String outXML;
    private String valueFromIncomingMessage;
    private FIXML incomingMessage;
    private FIXML outgoingMessage;
    private List<String> fieldsIncomeForWork;
    private List<String> fieldsOutgoingForWork;

    @Autowired
    public ModificationXML(Producer producer) {
        this.producer = producer;
    }


    /**
     * демаршализация входящего xml файла в объект FIXML с игнорированием пространства имен ,
     * извлечение целевых значений, демаршализация xml файла шаблона для отправки ,
     * установка значений , маршализация в строку , отправка сообщения
     */
    public void modificationAndSendToProducerXML(String xml) {

        getListOfFieldNameIncomeXML(xml);

        getListOfFieldNameOutgoingXML();

        marshalToStringOutgoingXML();
    }

    public List<String> getListOfFieldNameOutgoingXML() {
        JAXBContext jaxbContext;
        List<String> listFieldsNameOutgoing = null;

        try {
            jaxbContext = JAXBContext.newInstance(FIXML.class);
            Unmarshaller unmarshaller1 = jaxbContext.createUnmarshaller();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader2 = factory.createXMLStreamReader(getStringReader(outXML));
            outgoingMessage = unmarshaller1.unmarshal(xmlStreamReader2, FIXML.class).getValue();
            Field[] fieldsOutgoingMessage = ((TradeCaptureReportMessageT) outgoingMessage.getBatch()
                    .get(0).getMessage().get(0).getValue()).getClass().getDeclaredFields();
            listFieldsNameOutgoing = Arrays.stream(fieldsOutgoingMessage).map(Field::getName).collect(Collectors.toList());


        } catch (JAXBException | XMLStreamException e) {
            e.printStackTrace();
        }
        return listFieldsNameOutgoing;
    }

    /**
     * демаршализация входящего xml файла в объект FIXML с игнорированием пространства имен ,
     * извлечение целевых значений
     */
    public List<String> getListOfFieldNameIncomeXML(String xml) {
        JAXBContext jaxbContext;
        List<String> listFieldsNameIncome = null;
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
            valueFromIncomingMessage = ((TradeCaptureReportMessageT) incomingMessage.getBatch().get(0).getMessage().get(0).getValue()).getTrdID();

            Field[] fieldsIncomingMessage = ((TradeCaptureReportMessageT) incomingMessage.getBatch()
                    .get(0).getMessage().get(0).getValue()).getClass().getDeclaredFields();
            listFieldsNameIncome = Arrays.stream(fieldsIncomingMessage)
                    .map(Field::getName)
                    .collect(Collectors.toList());
            // fieldsValueForInsert = fieldsIncomeForWork.stream().map(x -> )

        } catch (IOException | JAXBException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return listFieldsNameIncome;
    }

    /**
     * маршализация в строку , отправка сообщения
     */
    public void marshalToStringOutgoingXML() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(FIXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(outgoingMessage, writer);
            String result = writer.toString();
            producer.sendMessage(result);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public StringReader getStringReader(String text) {
        return new StringReader(text);
    }

    public void setFieldsForWork(List<String> fields) {
        fieldsIncomeForWork = fields.stream().limit(4).collect(Collectors.toList());
        fields.subList(0,4);
        fieldsOutgoingForWork = fields.stream().skip(4).collect(Collectors.toList());
    }
}

//((TradeCaptureReportMessageT) outgoingMessage.getBatch().get(0).getMessage().get(0).getValue()).setTrdID(valueFromIncomingMessage);



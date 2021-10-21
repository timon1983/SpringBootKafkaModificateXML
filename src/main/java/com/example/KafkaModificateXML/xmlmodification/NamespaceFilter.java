package com.example.KafkaModificateXML.xmlmodification;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class NamespaceFilter extends XMLFilterImpl {

    private static final String NAMESPACE = "http://svn.msk.trd.ru/xsd/fixml";

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(NAMESPACE, localName, qName);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(NAMESPACE, localName, qName, attributes);
    }
}

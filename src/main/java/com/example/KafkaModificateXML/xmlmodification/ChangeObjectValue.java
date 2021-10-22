package com.example.KafkaModificateXML.xmlmodification;

import org.springframework.stereotype.Component;
import ru.trd.msk.svn.xsd.fixml.FIXML;
import ru.trd.msk.svn.xsd.fixml.TradeCaptureReportMessageT;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class ChangeObjectValue {

    public ChangeObjectValue(){
    }

    public Object changeXmlMessage(FIXML incomeMessage, FIXML outgoingMessage,
                                   List<String> fieldsIncomeForWork, List<String> fieldsOutgoingForWork){

        String inFieldName;
        String outFieldName;
        String inValue;

        for(int i = 0; i < fieldsIncomeForWork.size(); i++){
            inFieldName = fieldsIncomeForWork.get(i);
            outFieldName = fieldsOutgoingForWork.get(i);
            inValue = getValueByFieldName(inFieldName, incomeMessage);
            setValueByFieldName(outFieldName, inValue, outgoingMessage);
        }
        return outgoingMessage;
    }

    public String getValueByFieldName(String fieldName , FIXML fixml) {
        TradeCaptureReportMessageT tradeCapture = (TradeCaptureReportMessageT) fixml
                .getBatch()
                .get(0)
                .getMessage()
                .get(0)
                .getValue();
        Field field;
        String value = null;
        try {
            field = tradeCapture.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = (String) field.get(tradeCapture);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setValueByFieldName(String fieldName, String value, FIXML fixml){
        TradeCaptureReportMessageT tradeCapture = (TradeCaptureReportMessageT) fixml
                .getBatch()
                .get(0)
                .getMessage()
                .get(0)
                .getValue();
        Field field;
        try {
            field = tradeCapture.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(tradeCapture, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

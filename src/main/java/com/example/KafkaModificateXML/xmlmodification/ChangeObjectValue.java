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

    /** считывание значений заданных полей входящего сообщения
     * и запись их в заданые поля исходящего сообщения
     *
     * @param incomeMessage
     * @param outgoingMessage
     * @param fieldsIncomeForWork
     * @param fieldsOutgoingForWork
     * @return
     */
    public Object changeXmlMessage(FIXML incomeMessage, FIXML outgoingMessage,
                                   List<String> fieldsIncomeForWork, List<String> fieldsOutgoingForWork){

        String inFieldName;
        String outFieldName;
        Object inValue;
        if (!(fieldsIncomeForWork.isEmpty())) {

            for (int i = 0; i < fieldsIncomeForWork.size(); i++) {
                inFieldName = fieldsIncomeForWork.get(i);
                outFieldName = fieldsOutgoingForWork.get(i);
                inValue = getValueByFieldName(inFieldName, incomeMessage);
                setValueByFieldName(outFieldName, inValue, outgoingMessage);
            }
            return outgoingMessage;
        } else {
            return new Object();
        }
    }

    /** получение значение поля входящего сообщения по его имени через рефлексию
     *
     * @param fieldName
     * @param fixml
     * @return
     */
    public Object getValueByFieldName(String fieldName , FIXML fixml) {
        TradeCaptureReportMessageT tradeCapture = (TradeCaptureReportMessageT) fixml
                .getBatch()
                .get(0)
                .getMessage()
                .get(0)
                .getValue();
        Field field;
        Object value = null;
        try {
            field = tradeCapture.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(tradeCapture);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    /** вставка нового значения в поле исходящего сообщения по именю поля через рефлексию
     *
     * @param fieldName
     * @param value
     * @param fixml
     */
    public void setValueByFieldName(String fieldName, Object value, FIXML fixml){
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

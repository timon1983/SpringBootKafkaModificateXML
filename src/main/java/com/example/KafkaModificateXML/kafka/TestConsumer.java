package com.example.KafkaModificateXML.kafka;

import com.example.KafkaModificateXML.xmlmodification.ModificationXML;

public class TestConsumer {
   static String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<FIXML>\n" +
            "\t<Batch>\n" +
            "\t\t<TrdCaptRpt TrdID=\"4\" LastQty=\"1526.41\" Ccy=\"USD\" LastPx=\"80.88\" ExecTyp=\"F\" TransTyp=\"0\" TrdTyp=\"54\" ExecID=\"4\" TrdDt=\"2021-10-08\" TxnTm=\"2021-10-08T18:10:37.739+05:00\" ReqID=\"4\" RptID=\"4\" PrevlyRpted=\"Y\">\n" +
            "\t\t\t<Hdr SndgTm=\"2021-10-08T13:10:37.739Z\" SID=\"dogmaIMB\" TID=\"MX\" SSub=\"dogma\"/>\n" +
            "\t\t\t<Pty R=\"12\" Src=\"D\" ID=\"USER_CODE\"/>\n" +
            "\t\t\t<Pty R=\"16\" Src=\"D\" ID=\"dogma\"/>\n" +
            "\t\t\t<Pty R=\"1000\" Src=\"D\" ID=\"dogma\"/>\n" +
            "\t\t\t<Pty R=\"62\" Src=\"D\" ID=\"dogmaIMB\"/>\n" +
            "\t\t\t<Instrmt Sym=\"USD/RUB\" CFI=\"FFWPNV\"/>\n" +
            "\t\t\t<TrdLeg LastQty=\"1526.41\" LegCalcCcyLastQty=\"123456\" LastPx=\"80.88\" SettlDt=\"2021-10-08\" TrdPx=\"80.88\">\n" +
            "\t\t\t\t<Leg Side=\"2\"/>\n" +
            "\t\t\t</TrdLeg>\n" +
            "\t\t\t<TrdLeg LastQty=\"1526.41\" LegCalcCcyLastQty=\"123567.66\" LastPx=\"80.953124\" SettlDt=\"2021-10-11\" TrdPx=\"80.887312\">\n" +
            "\t\t\t\t<Leg Side=\"1\"/>\n" +
            "\t\t\t</TrdLeg>\n" +
            "\t\t\t<RptSide Side=\"2\" SettlCurrFxRtCalc=\"M\">\n" +
            "\t\t\t\t<Pty R=\"3\" Src=\"D\" ID=\"crmId\"/>\n" +
            "\t\t\t\t<Pty R=\"17\" Src=\"D\" ID=\"USER_CODE\"/>\n" +
            "\t\t\t\t<Pty R=\"27\" Src=\"D\" ID=\"DOGMA\"/>\n" +
            "\t\t\t</RptSide>\n" +
            "\t\t\t<Amt Typ=\"SPLITVALUE\" Amt=\"1373.77\" Ccy=\"USD\"/>\n" +
            "\t\t</TrdCaptRpt>\n" +
            "\t</Batch>\n" +
            "</FIXML>";



    public static void main(String[] args) {
      ModificationXML modificationXML = new ModificationXML();
      modificationXML.modificationAndSendToProducerXML(message);

    }

    public void start(){

    }

}

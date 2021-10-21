package com.example.KafkaModificateXML.service;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import ru.trd.msk.svn.xsd.fixml.FIXML;
import ru.trd.msk.svn.xsd.fixml.TradeCaptureReportMessageT;
import javax.xml.bind.*;
import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;


public class JaxbWorker {


    public static void main(String[] args) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FIXML.class);
            Unmarshaller unmarshaller1 = jaxbContext.createUnmarshaller();
            XMLFilter filter = new NamespaceFilter();
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            filter.setParent(xr);
            UnmarshallerHandler unmarshallerHandler = unmarshaller1.getUnmarshallerHandler();
            filter.setContentHandler(unmarshallerHandler);
            InputSource is = new InputSource();
            is.setCharacterStream(getStringReader(inXml));
            filter.parse(is);
            FIXML fixml1 = (FIXML) unmarshallerHandler.getResult();
            String g = ((TradeCaptureReportMessageT) fixml1.getBatch().get(0).getMessage().get(0).getValue()).getTrdID();
            Field[] fields = ((TradeCaptureReportMessageT) fixml1.getBatch().get(0).getMessage().get(0).getValue()).getClass().getDeclaredFields();
            Arrays.stream(fields).map(Field::getName).forEach(System.out::println);

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader2 = factory.createXMLStreamReader(getStringReader(xml));
            FIXML fixml2 = unmarshaller1.unmarshal(xmlStreamReader2, FIXML.class).getValue();
            ((TradeCaptureReportMessageT) fixml2.getBatch().get(0).getMessage().get(0).getValue()).setTrdID(g);

            Marshaller marshaller = jaxbContext.createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(fixml2, writer);
            String result = writer.toString();
            //System.out.println(result);

        } catch (JAXBException | XMLStreamException | IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static StringReader getStringReader(String text){
        return new StringReader(text);
    }

    private static String inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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
    private static String xml = "<FIXML xmlns=\"http://svn.msk.trd.ru/xsd/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" v=\"5.0\" xsi:schemaLocation=\"http://svn.msk.trd.ru/xsd/fixml http://svn.msk.trd.ru/xsd/fixml/trunk/fixml-tradecapture-impl-5-0-SP2.xsd\"><Batch><TrdCaptRpt ExecTyp=\"F\" RptID=\"MX37662194_6F\" TrdVer=\"1\" TransTyp=\"0\" RptTyp=\"0\" RptRefID=\"\" TrdID=\"37662193\" OrigTrdID=\"37662193\" TrdID2=\"39394575\" OrignTrdID2=\"39394575\" TrdTyp=\"54\" MktID=\"XOTC\" OrigTrdDt=\"2021-06-21\" TrdDt=\"2021-06-21\" ExecID=\"DOGMAIMB132_1\" MsgEvtSrc=\"Insert\" LastUpdateTm=\"2021-06-21T16:10:28Z\" TxnTm=\"2021-06-21T16:10:21\" BizDt=\"2021-09-16\" LinkID=\"\" PackageID=\"\" LastQtyVarnc=\"1002.\" TZTransactTime=\"2021-06-21T13:10:28\" Ccy=\"EUR\" LastQty=\"11.56\" LastPx=\"0.024221\" LastSpotRt=\"86.70242214533\" ValDt=\"2021-06-22\" MtchStat=\"0\" ExecID2=\"13\"><Extensions><Extension name=\"LastQtyVarnc2\"/><Extension name=\"LastQtyVarncCcy2\"/><Extension key=\"RptSide\" name=\"StrategyLinkID\">132/USLSK</Extension><Extension name=\"TZTransactTime\">2021-06-21T13:10:28</Extension><Extension name=\"mxLastQtyVarnc2ISO\"/><Extension name=\"mxLastQtyVarncISO\"/><Extension name=\"mxTradeEventType\"/><Extension name=\"mxEventTyp\"/><Extension key=\"RptSide\" name=\"Ccy\"/><Extension key=\"RptSide\" name=\"RsnCD\"/><Extension key=\"RptSide\" name=\"FillStationCd\"/><Extension key=\"nonDeliverableSettlement\" name=\"fxFixingDate\"/><Extension key=\"RtSrc\" name=\"RtSrc\"/><Extension key=\"Stip\" name=\"COLLAGRCATEG\">NONE</Extension><Extension name=\"TBFEETERM\"/><Extension name=\"TBFEETERMCCY\"/><Extension key=\"Stip\" name=\"dopETS\">No</Extension><Extension name=\"UTOFR\"/><Extension name=\"AgmtCcy\"/><Extension key=\"Undly\" name=\"FxRate\"/><Extension key=\"TrdLeg1\" name=\"RoundedNominal2\">1002.00</Extension><Extension key=\"TrdLeg2\" name=\"RoundedNominal2\">1002.28</Extension><Extension key=\"TrdLeg1\" name=\"SPLITVD\">N</Extension><Extension key=\"TrdLeg2\" name=\"SPLITVD\">N</Extension><Extension key=\"TrdLeg1\" name=\"FXNETTING\">No</Extension><Extension key=\"TrdLeg2\" name=\"FXNETTING\">No</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Typ\">101</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Desc\">CAP-NOM-_-_-INT-_</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Amt\">11.56</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"mxAmtISO\">11.56</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Ccy\">EUR</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"PaySide\">USLSK</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Rcvside\">FX_SB_OPT_FLOW</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"LegRefID\">2-1</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"Dt\">2021-06-22</Extension><Extension groupKey=\"Pmt\" key=\"1\" name=\"mxTrdVer\">1</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Typ\">101</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Desc\">CAP-NOM-_-_-INT-_</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Amt\">1002.28</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"mxAmtISO\">1002.28</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Ccy\">RUB</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"PaySide\">FX_SB_OPT_FLOW</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Rcvside\">USLSK</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"LegRefID\">2-1</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"Dt\">2021-06-22</Extension><Extension groupKey=\"Pmt\" key=\"2\" name=\"mxTrdVer\">1</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Typ\">101</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Desc\">CAP-NOM-_-_-INT-_</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Amt\">11.56</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"mxAmtISO\">11.56</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Ccy\">EUR</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"PaySide\">FX_SB_OPT_FLOW</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Rcvside\">USLSK</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"LegRefID\">1-1</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"Dt\">2021-06-21</Extension><Extension groupKey=\"Pmt\" key=\"3\" name=\"mxTrdVer\">1</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Typ\">101</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Desc\">CAP-NOM-_-_-INT-_</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Amt\">1002</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"mxAmtISO\">1002.00</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Ccy\">RUB</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"PaySide\">USLSK</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Rcvside\">FX_SB_OPT_FLOW</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"LegRefID\">1-1</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"Dt\">2021-06-21</Extension><Extension groupKey=\"Pmt\" key=\"4\" name=\"mxTrdVer\">1</Extension></Extensions><TrdLeg RptID=\"DOGMAIMB132_1\" RefID=\"37662193\" OrigTrdID=\"37662193\" LastQty=\"11.56\" LastPx=\"86.67820069204\" LegCalcCcyLastQty=\"1002\" SettlDt=\"2021-06-21\" SettlDt2=\"2021-06-21\" SettlTyp=\"1\"><Leg Sym=\"EUR/RUB\" Side=\"2\" SettlMeth=\"P\"/><Stip Typ=\"SPLITVD\" Val=\"N\"/><Stip Typ=\"FXNETTING\" Val=\"No\"/></TrdLeg><TrdLeg RptID=\"DOGMAIMB132_2\" RefID=\"37662192\" OrigTrdID=\"37662192\" LastQty=\"11.56\" LastPx=\"86.70242214533\" LegCalcCcyLastQty=\"1002.28\" SettlTyp=\"0\" SettlDt=\"2021-06-22\" SettlDt2=\"2021-06-22\"><Leg Sym=\"EUR/RUB\" Side=\"1\" SettlMeth=\"P\"/><Stip Typ=\"SPLITVD\" Val=\"N\"/><Stip Typ=\"FXNETTING\" Val=\"No\"/></TrdLeg><Amt Typ=\"TVAR\" Amt=\"0\"/><Amt Typ=\"VADJ\" Amt=\"0\"/><Pty ID=\"AFILATOV2\" Src=\"D\" R=\"12\"/><Pty ID=\"MX\" Src=\"D\" R=\"16\"/><Pty ID=\"dogma\" Src=\"D\" R=\"1000\"/><Pty ID=\"dogmaIMB\" Src=\"D\" R=\"62\"/><Pty ID=\"\" Src=\"D\" R=\"1117\"/><Instrmt ProdCmplx=\"CURRFXDSWLEG\" Sym=\"EUR/RUB\" CFI=\"FFWPNV\" Mult=\"1\"/><RptSide Side=\"2\" Txt=\"||||||\" StrategyLinkID=\"132/USLSK\" SettlCurrFxRtCalc=\"M\"><Stip Typ=\"FXNETTING\" Val=\"No\"/><Stip Typ=\"LOCALTCID\" Val=\"DOGMA\"/><Stip Typ=\"REMOTETCID\" Val=\"REVJ\"/><Stip Typ=\"LORO_LEG1_CUR1\" Val=\"NOSTRO\"/><Stip Typ=\"LORO_LEG1_CUR2\" Val=\"NOSTRO\"/><Stip Typ=\"LORO_LEG2_CUR1\" Val=\"NOSTRO\"/><Stip Typ=\"LORO_LEG2_CUR2\" Val=\"NOSTRO\"/><Stip Typ=\"DOPETS\" Val=\"No\"/><Pty ID=\"USLSK\" Src=\"D\" R=\"3\"><Sub ID=\"СХПК УСОЛЬСКИЙ СВИНОКОМПЛЕКС\" Typ=\"5\"/></Pty><Pty ID=\"FX_SB_OPT_FLOW\" Src=\"D\" R=\"38\"/><Pty ID=\"               \" Src=\"D\" R=\"83\"/><Pty ID=\"**SBRB\" Src=\"D\" R=\"1\"/><Stip Typ=\"COLLAGRCATEG\" Val=\"NONE\"/><Stip Typ=\"GOAL\" Val=\"Current activity\"/><ContAmt ContAmtTyp=\"101\" ContAmtValu=\"0.\" ContAmtCurr=\"RUB\"/><ContAmt ContAmtTyp=\"102\" ContAmtValu=\"0.\" ContAmtCurr=\"RUB\"/><ContAmt ContAmtTyp=\"103\" ContAmtValu=\"0.\" ContAmtCurr=\"RUB\"/></RptSide><Attchmnt Name=\"SalesCredit\" Clsfn=\"posttrade/salescredit\"><SalesCredit StrSal=\"N\" SPG=\"N\"><Total Amt=\"0\" Typ=\"1\"><Details Amt=\"0.\" Typ=\"1\" Ccy=\"USD\" CcyB=\"RUB\" FxRtA=\"73.24375\" FxRtM=\"73.24375\" Factor=\"1.\" TPrice=\"86.7012\"/><Details Amt=\"0.\" Typ=\"2\" Ccy=\"USD\" CcyB=\"EUR\" FxRtA=\"1.18915\" FxRtM=\"1.18915\" Factor=\"0.\" B2B=\"N\" OrdB=\"N\" NigTrd=\"N\"/><Details Amt=\"0\" Typ=\"3\" Ccy=\"USD\" CcyB=\"USD\" FxRtA=\"1.000000\" FxRtM=\"1.000000\"/></Total></SalesCredit></Attchmnt><Amt Typ=\"PREM\" Amt=\"0\" Ccy=\"USD\"/><Pmt Typ=\"101\" Desc=\"CAP-NOM-_-_-INT-_\" Amt=\"11.56\" mxAmtISO=\"11.56\" Ccy=\"EUR\" PaySide=\"USLSK\" RcvSide=\"FX_SB_OPT_FLOW\" LegRefID=\"2-1\" Dt=\"2021-06-22\" mxTrdVer=\"1\"/><Pmt Typ=\"101\" Desc=\"CAP-NOM-_-_-INT-_\" Amt=\"1002.28\" mxAmtISO=\"1002.28\" Ccy=\"RUB\" PaySide=\"FX_SB_OPT_FLOW\" RcvSide=\"USLSK\" LegRefID=\"2-1\" Dt=\"2021-06-22\" mxTrdVer=\"1\"/><Pmt Typ=\"101\" Desc=\"CAP-NOM-_-_-INT-_\" Amt=\"11.56\" mxAmtISO=\"11.56\" Ccy=\"EUR\" PaySide=\"FX_SB_OPT_FLOW\" RcvSide=\"USLSK\" LegRefID=\"1-1\" Dt=\"2021-06-21\" mxTrdVer=\"1\"/><Pmt Typ=\"101\" Desc=\"CAP-NOM-_-_-INT-_\" Amt=\"1002\" mxAmtISO=\"1002.00\" Ccy=\"RUB\" PaySide=\"USLSK\" RcvSide=\"FX_SB_OPT_FLOW\" LegRefID=\"1-1\" Dt=\"2021-06-21\" mxTrdVer=\"1\"/></TrdCaptRpt></Batch></FIXML>";

}

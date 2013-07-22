package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import static org.junit.Assert.assertEquals;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.soitoolkit.commons.mule.util.MiscUtil;

import se.skltp.adapterservices.takecare.lab.LabRepliesGet;
import se.skltp.adapterservices.takecare.lab.labrepliesget.request.X2Message;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.JaxbHelper;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil;

public class LabRepliesGetRequestTransformerTest {
    
    private static final JaxbUtil jaxbUtil_outgoing = new JaxbUtil(LabRepliesGet.class);

    @Test
    public void testTransformer_ok() throws Exception {

        String input = MiscUtil
                .readFileAsString("src/test/resources/testfiles/LabRepliesGet/request-input.xml");

        LabRepliesGetRequestTransformer transformer = new LabRepliesGetRequestTransformer();
        String result = (String) transformer.pojoTransform(input, "UTF-8");

        LabRepliesGet outgoing = (LabRepliesGet) jaxbUtil_outgoing.unmarshal(result);

        assertEquals(TakeCareUtil.HSAID, outgoing.getCareunitidtype());
        assertEquals("SE2321000016-1HZ3", outgoing.getCareunitid());
        assertEquals(TakeCareUtil.EXTERNAL_USER, outgoing.getExternaluser());
        assertEquals("", outgoing.getTcusername());
        assertEquals("", outgoing.getTcpassword());

        X2Message message = new X2Message();
        message = (X2Message) JaxbHelper.transform(message, "urn:X2Message:LabRepliesGet:Request", outgoing.getXml());

        assertEquals("191212121212", message.getPatientId());
        XMLGregorianCalendar startDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2013-01-12");
        assertEquals(startDate, message.getStartDate());
        XMLGregorianCalendar endDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2013-05-30");
        assertEquals(endDate, message.getEndDate());
        assertEquals(TakeCareUtil.INVOKING_SYSTEM, message.getInvokingSystem());
        assertEquals(TakeCareUtil.REQUEST, message.getMsgType());
    }
}
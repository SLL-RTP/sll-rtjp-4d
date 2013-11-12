package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.jws.WebService;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import se.skltp.adapterservices.takecare.lab.LabSoap;
import se.skltp.adapterservices.takecare.lab.labrepliesget.request.X2Message;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.NamespaceFilter;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.XMLTestUtil;

@WebService
public class LabRepliesGetTestProducer implements LabSoap {

    public static final String TEST_ID_OK = "194301013787";
    public static final String TEST_ID_FAULT_INVALID_ID = "-1";
    public static final String TEST_ID_FAULT_TIMEOUT = "0";

    private static final Logger log = LoggerFactory
            .getLogger(LabRepliesGetTestProducer.class);
    private static final RecursiveResourceBundle rb = new RecursiveResourceBundle(
            "takecare-adapter-lab-service-config");
    private static final long SERVICE_TIMOUT_MS = Long.parseLong(rb
            .getString("SERVICE_TIMEOUT_MS"));
    private static final JaxbUtil jaxbUtil_X2Message_request = new JaxbUtil(
            se.skltp.adapterservices.takecare.lab.labrepliesget.request.X2Message.class);

    @Override
    public String getLabIndex(String tcusername, String tcpassword,
            String externaluser, String careunitidtype, String careunitid,
            String xml) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String labRepliesGet(String tcusername, String tcpassword,
            String externaluser, String careunitidtype, String careunitid,
            String xml) {
        String result = null;
        
        log.debug("Incoming username to TakeCare '{}'", tcusername);
        log.debug("Incoming password to TakeCare '{}'", tcpassword);
        log.debug("Incoming externaluser to TakeCare '{}'", externaluser);
        log.debug("Incoming careunitidtype to TakeCare '{}'", careunitidtype);
        log.debug("Incoming careunitid to TakeCare '{}'", careunitid);
        log.debug("Incoming xml to TakeCare '{}'", xml);

        String patientId = null;
        if (xml != null && !xml.isEmpty()) {

            xml = setNamespace("urn:X2Message:LabRepliesGet:Request", xml);
            X2Message x2 = (X2Message) jaxbUtil_X2Message_request
                    .unmarshal(xml);

            patientId = x2.getPatientId();
        }

        if (TEST_ID_FAULT_INVALID_ID.equals(patientId)) {
            result = XMLTestUtil.createErrorResponse(externaluser, careunitidtype, careunitid);
        } else if (TEST_ID_FAULT_TIMEOUT.equals(patientId)) {
            try {
                Thread.sleep(SERVICE_TIMOUT_MS + 1000);
            } catch (InterruptedException e) {
            }
        } else if (TEST_ID_OK.equals(patientId)) {
            result =  XMLTestUtil.createOkResponse(tcusername, tcpassword, externaluser, careunitidtype, careunitid, patientId);
        } else {
           result = XMLTestUtil.createCommonResponse(tcusername, tcpassword, externaluser, careunitidtype, careunitid, patientId); 
        }

        log.debug("Returning result=[{}]", result);
        return result;

    }

    private String setNamespace(String namespace, String xml) {
        // Create an XMLReader to use with our filter
        XMLReader reader;
        try {
            reader = XMLReaderFactory.createXMLReader();

            // Create the filter (to add namespace) and set the xmlReader as
            // its parent.
            NamespaceFilter inFilter = new NamespaceFilter(namespace, true);
            inFilter.setParent(reader);
            Reader sr = new StringReader((String) xml);
            // Prepare the input, in this case a java.io.File (output)
            InputSource is = new InputSource(sr);

            // Create a SAXSource specifying the filter
            SAXSource source = new SAXSource(inFilter, is);
            Writer outWriter = new StringWriter();
            StreamResult result = new StreamResult(outWriter);
            TransformerFactory.newInstance().newTransformer()
                    .transform(source, result);
           return outWriter.toString();


        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException(e);
        }

    }

}

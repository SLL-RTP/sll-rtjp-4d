package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakecareAdapterLabServiceMuleServer.getAddress;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget.LabRepliesGetTestProducer.TEST_ID_FAULT_INVALID_ID;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget.LabRepliesGetTestProducer.TEST_ID_FAULT_TIMEOUT;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget.LabRepliesGetTestProducer.TEST_ID_OK;

import java.util.Date;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;
import org.soitoolkit.refapps.sd.sample.wsdl.v1.Fault;

import se.riv.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeResponseType;
import se.riv.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultType;
import se.skltp.adapterservices.takecare.lab.LabRepliesGet;
import se.skltp.adapterservices.takecare.lab.labrepliesget.response.X2Message;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareDateHelper;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil;

public class LabRepliesGetIntegrationTest extends AbstractTestCase {

    private static final Logger log = LoggerFactory
            .getLogger(LabRepliesGetIntegrationTest.class);

    private static final String EXPECTED_ERR_TIMEOUT_MSG = "Read timed out";
    private static final String DEFAULT_SERVICE_ADDRESS = getAddress("LABREPLIESGET_INBOUND_URL");
    private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
    private AbstractJmsTestUtil jmsUtil = null;
    private static final JaxbUtil jaxbUtil_LabRepliesGet = new JaxbUtil(LabRepliesGet.class);
    private static final JaxbUtil jaxbUtil_X2Message_response = new JaxbUtil(X2Message.class);
    private static final JaxbUtil jaxbUtil_X2Message_request = new JaxbUtil(
            se.skltp.adapterservices.takecare.lab.labrepliesget.request.X2Message.class);

    public LabRepliesGetIntegrationTest() {

        // Only start up Mule once to make the tests run faster...
        // Set to false if tests interfere with each other when Mule is started
        // only once.
        setDisposeContextPerClass(true);
    }

    protected String getConfigResources() {
        return "soitoolkit-mule-jms-connector-activemq-embedded.xml," 
                + "takecare-adapter-lab-service-common.xml,"
                + "LabRepliesGet-service.xml,"
                + "teststub-services/LabRepliesGet-teststub-service.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        doSetUpJms();

    }

    private void doSetUpJms() {
        // TODO: Fix lazy init of JMS connection et al so that we can create
        // jmsutil in the declaration
        // (The embedded ActiveMQ queue manager is not yet started by Mule when
        // jmsutil is delcared...)
        if (jmsUtil == null)
            jmsUtil = new ActiveMqJmsTestUtil();

        // Clear queues used for error handling
        jmsUtil.clearQueues(ERROR_LOG_QUEUE);
    }

    @Test
    public void test_ok() throws Fault {
        String patientId = "194301013787";
        LabRepliesGetTestConsumer consumer = new LabRepliesGetTestConsumer(
                DEFAULT_SERVICE_ADDRESS);

        GetClinicalChemistryLabOrderOutcomeResponseType response = consumer
                .callService(patientId, null, null);
        List<LabResultType> labResults = response.getLabResults();
        assertEquals(2, labResults.size());
        assertEquals(2, labResults.get(0).getAnalysis().size());

    }

    //@Test
    public void test_fault_invalidInput() throws Exception {
        try {

            LabRepliesGetTestConsumer consumer = new LabRepliesGetTestConsumer(
                    DEFAULT_SERVICE_ADDRESS);
            GetClinicalChemistryLabOrderOutcomeResponseType response = consumer.callService(TEST_ID_FAULT_INVALID_ID, null, null);
            fail("expected fault, but got a response of type: "
                    + ((response == null) ? "NULL" : response.getClass()
                            .getName()));
        } catch (SOAPFaultException e) {

            assertEquals("Invalid Id: " + TEST_ID_FAULT_INVALID_ID,
                    e.getMessage());

        }
    }

    // @Test
    public void test_fault_timeout() throws Fault {
        try {
            LabRepliesGetTestConsumer consumer = new LabRepliesGetTestConsumer(
                    DEFAULT_SERVICE_ADDRESS);
            GetClinicalChemistryLabOrderOutcomeResponseType response = consumer.callService(TEST_ID_FAULT_TIMEOUT, null, null);
            fail("expected fault, but got a response of type: "
                    + ((response == null) ? "NULL" : response.getClass()
                            .getName()));
        } catch (SOAPFaultException e) {
            assertTrue("Unexpected error message: " + e.getMessage(), e
                    .getMessage().startsWith(EXPECTED_ERR_TIMEOUT_MSG));
        }

        // Sleep for a short time period to allow the JMS response message to be
        // delivered, otherwise ActiveMQ data store seems to be corrupt
        // afterwards...
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

}

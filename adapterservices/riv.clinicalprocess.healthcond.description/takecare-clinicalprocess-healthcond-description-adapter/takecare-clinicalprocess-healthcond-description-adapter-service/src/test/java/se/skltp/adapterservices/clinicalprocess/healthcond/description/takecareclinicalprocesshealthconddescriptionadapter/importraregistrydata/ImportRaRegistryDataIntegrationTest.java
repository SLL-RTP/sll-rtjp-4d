package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import static org.junit.Assert.assertEquals;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.TakecareClinicalprocessHealthcondDescriptionAdapterMuleServer.getAddress;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata.ImportRaRegistryDataTestProducer.TESTID_INVALID;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata.ImportRaRegistryDataTestProducer.TESTID_OK;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;
import org.soitoolkit.commons.mule.util.MiscUtil;

import se.riv.clinicalprocess.healthcond.description.registerradsdataresponder.v1.ResultCodeEnum;

public class ImportRaRegistryDataIntegrationTest extends AbstractTestCase {

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataIntegrationTest.class);

  private static final String EXPECTED_ERR_TIMEOUT_MSG = "Read timed out";

  private static final String DEFAULT_SERVICE_ADDRESS = getAddress("IMPORTRAREGISTRYDATA_INBOUND_URL");

  private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
  private AbstractJmsTestUtil jmsUtil = null;

  public ImportRaRegistryDataIntegrationTest() {

    // Only start up Mule once to make the tests run faster...
    // Set to false if tests interfere with each other when Mule is started only
    // once.
    setDisposeContextPerClass(true);
  }

  protected String getConfigResources() {
    return "soitoolkit-mule-jms-connector-activemq-embedded.xml," +

    "takecare-clinicalprocess-healthcond-description-adapter-common.xml," + "importRaRegistryData-service.xml,"
        + "teststub-services/importRaRegistryData-teststub-service.xml";
  }

  @Override
  protected void doSetUp() throws Exception {
    super.doSetUp();

    doSetUpJms();

  }

  private void doSetUpJms() {
    // TODO: Fix lazy init of JMS connection et al so that we can create jmsutil
    // in the declaration
    // (The embedded ActiveMQ queue manager is not yet started by Mule when
    // jmsutil is declared...)
    if (jmsUtil == null)
      jmsUtil = new ActiveMqJmsTestUtil();

    // Clear queues used for error handling
    jmsUtil.clearQueues(ERROR_LOG_QUEUE);
  }

  @Test
  public void test_ok() {

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);
    String request = MiscUtil
        .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-ok.xml");
    String result = consumer.callService(request);

    Map<String, String> map = extractResponseVariables(result);

    assertEquals("ResultCode", ResultCodeEnum.OK.toString(), map.get("ResultCode"));
    assertEquals("comment", "PatientId " + TESTID_OK + " was ok. Thank you.", map.get("comment"));

  }

  @Test
  public void test_invalid_input() {

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);
    String request = MiscUtil
        .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-invalidinput.xml");
    String result = consumer.callService(request);

    Map<String, String> map = extractResponseVariables(result);

    assertEquals("ResultCode", ResultCodeEnum.ERROR.toString(), map.get("ResultCode"));
    assertEquals("comment", "PatientId " + TESTID_INVALID + " is invalid.", map.get("comment"));

  }

  @Test
  public void test_fault() throws Exception {

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);
    String request = MiscUtil
        .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-invalidinput.xml");
    String result = consumer.callService(request);

    Map<String, String> map = extractResponseVariables(result);

    assertEquals("ResultCode", ResultCodeEnum.ERROR.toString(), map.get("ResultCode"));
    assertEquals("comment", "PatientId " + TESTID_INVALID + " is invalid.", map.get("comment"));
  }

  private Map<String, String> extractResponseVariables(String result) {
    Map<String, String> map = new HashMap<String, String>();
    String[] entries = result.split("\n");
    for (String entry : entries) {
      String[] keyValue = entry.split("=");
      map.put(keyValue[0], keyValue[1]);
    }
    return map;
  }

}

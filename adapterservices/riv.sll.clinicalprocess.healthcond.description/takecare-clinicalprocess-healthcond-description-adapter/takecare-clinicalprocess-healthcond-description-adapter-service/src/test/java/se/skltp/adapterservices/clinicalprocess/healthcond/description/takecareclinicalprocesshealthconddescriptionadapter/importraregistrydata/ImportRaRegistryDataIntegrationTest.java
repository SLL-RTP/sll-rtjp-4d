package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import static org.junit.Assert.assertEquals;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.TakecareClinicalprocessHealthcondDescriptionAdapterMuleServer.getAddress;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata.ImportRaRegistryDataTestProducer.TESTID_INVALID;
import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata.ImportRaRegistryDataTestProducer.TESTID_OK;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;
import org.soitoolkit.commons.mule.util.MiscUtil;

import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.ResultCodeEnum;

public class ImportRaRegistryDataIntegrationTest extends AbstractTestCase {

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataIntegrationTest.class);

  private static final String EXPECTED_ERR_TIMEOUT_MSG = "Read timed out";

  private static final String DEFAULT_SERVICE_ADDRESS = getAddress("IMPORTRAREGISTRYDATA_INBOUND_URL");

  private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
  private AbstractJmsTestUtil jmsUtil = null;
  private final String encryptionKey = "TheBestSecretKey";

  public ImportRaRegistryDataIntegrationTest() {

    // Only start up Mule once to make the tests run faster...
    // Set to false if tests interfere with each other when Mule is started only
    // once.
    setDisposeContextPerClass(true);
  }

  protected String getConfigResources() {
    return "soitoolkit-mule-jms-connector-activemq-embedded.xml,"
        + "takecare-clinicalprocess-healthcond-description-adapter-common.xml," + "importRaRegistryData-service.xml,"
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
  public void test_ok() throws Exception {

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);
    
    Map<String, String> params = new HashMap<String, String>();
    params.put("registry", "rareg");
    params.put("location", "Karolinska Universitetssjukhuset");
    params.put("sender", "Take Care");
    String xml = MiscUtil
        .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-ok.xml");
    //AesUtil aesUtil = new AesUtil();
    //aesUtil.setSecretKey(encryptionKey);
    //params.put("xml", aesUtil.encrypt(xml));
    params.put("xml", xml);
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("x-vp-auth-cert", "HSAID1");
    
    
    String request = generateQueryString(params);
    
    Map<String, String> result = consumer.callService(request, headers);

    Map<String, String> map = extractResponseVariables((String)result.get("http.response"));

    assertEquals("ResultCode", ResultCodeEnum.OK.toString(), map.get("ResultCode"));
    assertEquals("comment", "PatientId " + TESTID_OK + " was ok. Thank you.", map.get("comment"));

  }


  @Test
  public void test_invalid_input() throws Exception{

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);

    Map<String, String> params = new HashMap<String, String>();
    params.put("registry", "rareg");
    params.put("location", "Karolinska Universitetssjukhuset");
    params.put("sender", "Take Care");
    String xml = MiscUtil
    .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-invalidinput.xml");
    //AesUtil aesUtil = new AesUtil();
    //aesUtil.setSecretKey(encryptionKey);
    //params.put("xml", aesUtil.encrypt(xml));
    params.put("xml", xml);
    String request = generateQueryString(params);
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("x-vp-auth-cert", "HSAID1");
    
    Map<String, String> result = consumer.callService(request, headers);

    
    assertEquals("http.status", "HTTP/1.1 400 Bad Request", result.get("http.status"));
    Map<String,String> httpResult = extractResponseVariables((String)result.get("http.response"));
    assertEquals("ResultCode", "Error", httpResult.get("ResultCode"));
    assertEquals("comment", "soap:Server : PatientId " + TESTID_INVALID + " is invalid.", httpResult.get("comment"));

  }

  @Test
  public void test_fault() throws Exception {

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(DEFAULT_SERVICE_ADDRESS);
 
    Map<String, String> params = new HashMap<String, String>();
    params.put("registry", "rareg");
    params.put("location", "Karolinska Universitetssjukhuset");
    params.put("sender", "Take Care");
    String xml = MiscUtil
    .readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-invalidinput.xml");
    //AesUtil aesUtil = new AesUtil();
    //aesUtil.setSecretKey(encryptionKey);
    //params.put("xml", aesUtil.encrypt(xml));
    params.put("xml", xml);
    String request = generateQueryString(params);
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("x-vp-auth-cert", "HSAID1");
    
    Map<String, String> result = consumer.callService(request, headers);

    Map<String, String> map = extractResponseVariables((String)result.get("http.response"));
    
    assertEquals("http.status", "HTTP/1.1 400 Bad Request", result.get("http.status"));
    assertEquals("comment", "soap:Server : PatientId " + TESTID_INVALID + " is invalid.", map.get("comment"));
  }

  private Map<String, String> extractResponseVariables(String result) {
    Map<String, String> map = new HashMap<String, String>();
    String[] entries = result.split("\n");
    for (String entry : entries) {
      String[] keyValue = entry.split("=");
      if (keyValue.length == 2) {
        map.put(keyValue[0], keyValue[1]);
      }
    }
    return map;
  }

  Map<String, String> decodeParameters(Map<String, String> params) throws Exception {

    Map<String, String> result = new HashMap<String, String>();

    for (Map.Entry<String, String> entry : params.entrySet()) {

      result.put(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
    }

    return result;

  }
  public String generateQueryString(Map<String, String> params) throws Exception {
    StringBuffer result = new StringBuffer();
    params = decodeParameters(params);
    for (Map.Entry<String, String> entry : params.entrySet())
    {
        if (result.length() > 0) result.append("&");
        result.append(entry.getKey());
        result.append("=");
        result.append(entry.getValue());
    }
    return result.toString();
  }


}

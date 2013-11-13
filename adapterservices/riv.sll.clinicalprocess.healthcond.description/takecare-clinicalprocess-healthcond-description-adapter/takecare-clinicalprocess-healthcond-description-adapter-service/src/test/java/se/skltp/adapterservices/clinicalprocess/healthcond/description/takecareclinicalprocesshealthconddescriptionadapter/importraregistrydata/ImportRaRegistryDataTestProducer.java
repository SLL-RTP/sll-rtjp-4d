package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;

import se.sll.clinicalprocess.healthcond.description.registerradsdata.v1.rivtabp21.RegisterRaDSDataResponderInterface;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataResponseType;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataType;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.ResultCodeEnum;

@WebService
public class ImportRaRegistryDataTestProducer implements RegisterRaDSDataResponderInterface {

  
  public static final String TESTID_OK = "191212121212";
  public static final String TESTID_INVALID = "-1";
  public static final String TESTID_FAULT = "-2";
  public static final String TESTID_TIMEOUT = "0";

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataTestProducer.class);
  private static final RecursiveResourceBundle rb = new RecursiveResourceBundle(
      "takecare-clinicalprocess-healthcond-description-adapter-config");
  private static final long SERVICE_TIMOUT_MS = Long.parseLong(rb.getString("SERVICE_TIMEOUT_MS"));

  @Resource
  private WebServiceContext wctx;
  
  @WebMethod
  public RegisterRaDSDataResponseType registerRaDSData(String logicalAddress, RegisterRaDSDataType raDSData) {
    log.debug("Received message:" + raDSData.toString());

    RegisterRaDSDataResponseType result = new RegisterRaDSDataResponseType();

    

    if (wctx == null) {
      log.error("WebServiceContext is null");
    } else {
      MessageContext mctx = wctx.getMessageContext();
      @SuppressWarnings("unchecked")
      Map<String, Object> headers = (Map<String, Object>) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);    
      String certText = (String)headers.get("x-vp-auth-cert");
      log.debug("Header 'x-vp-auth-cert'=" + certText);
    }
    
    String id = raDSData.getPatient().getPersonId().getId();

    // Return an error-message if invalid id
    if (TESTID_INVALID.equals(id)) {
        throw new RuntimeException("PatientId " + id + " is invalid.");
    }

    // Throw a runtime exception
    if (TESTID_FAULT.equals(id)) {
      throw new RuntimeException("Invalid Id: " + id);
    }

    // Force a timeout if zero Id
    if (TESTID_TIMEOUT.equals(id)) {
      try {
        Thread.sleep(SERVICE_TIMOUT_MS + 1000);
      } catch (InterruptedException e) {
      }
    }

    // Produce the ok response
    result.setResultCode(ResultCodeEnum.OK);
    result.setComment("PatientId " + id + " was ok. Thank you.");
    return result;
  }

}

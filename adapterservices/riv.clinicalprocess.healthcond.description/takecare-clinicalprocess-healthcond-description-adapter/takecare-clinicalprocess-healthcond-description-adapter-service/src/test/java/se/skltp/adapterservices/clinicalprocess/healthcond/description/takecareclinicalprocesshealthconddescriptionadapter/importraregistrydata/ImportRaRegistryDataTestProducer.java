package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;

import se.riv.clinicalprocess.healthcond.description.registerradsdata.v1.rivtabp21.RegisterRaDSDataResponderInterface;
import se.riv.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataResponseType;
import se.riv.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataType;
import se.riv.clinicalprocess.healthcond.description.registerradsdataresponder.v1.ResultCodeEnum;

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

  @Override
  public RegisterRaDSDataResponseType registerRaDSData(String logicalAddress, RegisterRaDSDataType raDSData) {
    log.debug("Received message:" + raDSData.toString());

    RegisterRaDSDataResponseType result = new RegisterRaDSDataResponseType();

    String id = raDSData.getPatient().getPatientId();

    // Return an error-message if invalid id
    if (TESTID_INVALID.equals(id)) {
      result.setResultCode(ResultCodeEnum.ERROR);
      result.setComment("PatientId " + id + " is invalid.");
      return result;
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

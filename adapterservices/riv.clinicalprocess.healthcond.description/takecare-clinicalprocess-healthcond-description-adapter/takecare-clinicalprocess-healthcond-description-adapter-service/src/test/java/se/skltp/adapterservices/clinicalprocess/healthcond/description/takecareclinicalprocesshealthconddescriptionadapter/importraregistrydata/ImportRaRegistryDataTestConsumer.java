package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import static se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.TakecareClinicalprocessHealthcondDescriptionAdapterMuleServer.getAddress;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;

public class ImportRaRegistryDataTestConsumer {

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataTestConsumer.class);

  private static final RecursiveResourceBundle rb = new RecursiveResourceBundle(
      "takecare-clinicalprocess-healthcond-description-adapter-config");
  private static final int SERVICE_TIMOUT_MS = Integer.parseInt(rb.getString("SERVICE_TIMEOUT_MS"));

  private HttpClient httpclient = null;
  private String serviceAddress = null;

  public ImportRaRegistryDataTestConsumer(String serviceAddress) {

    this.serviceAddress = serviceAddress;
    // Set connection timeout
    HttpParams params = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(params, SERVICE_TIMOUT_MS);
    HttpConnectionParams.setSoTimeout(params, SERVICE_TIMOUT_MS);

    httpclient = new DefaultHttpClient(params);
  }

  public static void main(String[] args) {
    String serviceAddress = getAddress("IMPORTRAREGISTRYDATA_INBOUND_URL");
    String xml = "191212121212";

    ImportRaRegistryDataTestConsumer consumer = new ImportRaRegistryDataTestConsumer(serviceAddress);

    String response = consumer.callService(xml);

    log.info("Returned value = " + response);
  }

  /**
   * Call the ImportRegistrydata Service with a http post
   * 
   * @param data
   *          xml structure to import
   * @return string http response as a string
   */
  public String callService(String data) {

    log.debug("Calling soap service with data " + data.toString());
    String result = null;

    try {
      HttpPost httpPost = new HttpPost(serviceAddress);
      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      StringEntity entity = new StringEntity(data);
      httpPost.setEntity(entity);
      HttpResponse response = httpclient.execute(httpPost);

      try {
        HttpEntity resEnt = response.getEntity();
        result = EntityUtils.toString(resEnt);
        EntityUtils.consume(resEnt);
      } finally {
        httpPost.releaseConnection();
      }

    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Oooops", e);
    } catch (ClientProtocolException e) {
      throw new RuntimeException("Oooops", e);
    } catch (IOException e) {
      throw new RuntimeException("Oooops", e);
    }
    return result;
  }
}
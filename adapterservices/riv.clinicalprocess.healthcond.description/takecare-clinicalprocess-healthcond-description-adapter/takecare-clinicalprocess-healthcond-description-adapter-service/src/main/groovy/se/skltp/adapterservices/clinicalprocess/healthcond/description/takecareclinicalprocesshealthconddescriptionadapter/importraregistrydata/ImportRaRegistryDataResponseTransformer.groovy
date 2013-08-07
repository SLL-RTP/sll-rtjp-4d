package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;



import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportRaRegistryDataResponseTransformer extends AbstractMessageTransformer {

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataResponseTransformer.class);

  /**
   * Message aware transformer that transforms the response from RegisterRaDSDataResponse
   * 
   * @param message message to be transformed
   * @param outputEncoding 
   */
  @Override
  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

    // Perform any message aware processing here, otherwise delegate as much as possible to pojoTransform() for easier unit testing

    return pojoTransform(message.getPayload(), outputEncoding);
  }

  /**
   * Rensform the RegisterRaDSDataResponse that is embedded in a Envelope
   */
  public Object pojoTransform(Object src, String outputEncoding) throws TransformerException {
    log.debug("Incoming payload: [{}]", src);

    def result = null

    def envelope = new XmlSlurper().parseText(src)

    if (envelope.Body.Fault.size() == 1) {
      def f = envelope.Body.Fault
      result = new StringBuffer()
      result << 'ResultCode=' << 'Error'
      result << '\ncomment=' << f.faultcode.text() << ' : ' << f.faultstring.text()
    } else {
      def r = envelope.Body.RegisterRaDSDataResponse
      result = new StringBuffer()
      result << 'ResultCode=' << r.ResultCode.text()
      result << '\ncomment=' << r.comment.text()
    }

    log.debug("Outgoing payload: [{}]", result.toString())
    return result.toString()
  }
}
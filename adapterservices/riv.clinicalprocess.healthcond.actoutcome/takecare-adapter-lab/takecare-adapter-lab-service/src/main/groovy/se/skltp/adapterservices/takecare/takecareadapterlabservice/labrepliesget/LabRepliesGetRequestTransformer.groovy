package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;

import java.util.Date;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
import se.skltp.adapterservices.takecare.lab.LabRepliesGet;
import se.skltp.adapterservices.takecare.lab.labrepliesget.request.X2Message;

import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareDateHelper.yyyyMMddHHmmss;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil.EXTERNAL_USER;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil.HSAID;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil.INVOKING_SYSTEM;
import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareUtil.REQUEST;



public class LabRepliesGetRequestTransformer extends AbstractMessageTransformer {

	private static final Logger log = LoggerFactory.getLogger(LabRepliesGetRequestTransformer.class);

    private static final JaxbUtil jaxbUtil_incoming = new JaxbUtil(GetClinicalChemistryLabOrderOutcomeType.class);
	
	String externalUser = null
	String invokingSystem = null

    /**
     * Message aware transformer that ...
     */
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

        // Perform any message aware processing here, otherwise delegate as much as possible to pojoTransform() for easier unit testing

        return pojoTransform(((Object[]) message.getPayload())[1], outputEncoding);

    }

	/**
     * Simple pojo transformer method that can be tested with plain unit testing...
	 */
	protected Object pojoTransform(Object src, String encoding) throws TransformerException {

		log.debug("Incoming payload {}", String.valueOf(src));
		
	       try {
	            GetClinicalChemistryLabOrderOutcomeType incomingRequest = (GetClinicalChemistryLabOrderOutcomeType) jaxbUtil_incoming
	                    .unmarshal(src);

                def builder = new StreamingMarkupBuilder(useDoubleQuotes: true)
                builder.encoding = 'UTF-8'
                def xml = builder.bind {
                    X2Message(InvokingSystem:"${invokingSystem}", MsgType:"${REQUEST}", Time:"${yyyyMMddHHmmss(new Date())}") {
                        PatientId("${incomingRequest.getPatientId()}")
                        if (incomingRequest.getStartDate() != null) {
                            StartDate("${incomingRequest.getStartDate()}")
                        }
                        if (incomingRequest.getEndDate() != null) {
                            EndDate("${incomingRequest.getEndDate()}")
                        }
                    }
                }
                
                builder = new StreamingMarkupBuilder(useDoubleQuotes: true)
                builder.encoding = 'UTF-8'
                def outgoingRequest = builder.bind {
                    mkp.declareNamespace("":'http://tempuri.org/')
                    LabRepliesGet {
                      tcusername        ''
                      tcpassword        ''
                      externaluser      "${externalUser}"
                      careunitidtype    "${HSAID}"
                      careunitid        "${incomingRequest.getHealthcareFacilityId()}"
                      delegate.xml {
                            mkp.yieldUnescaped("<![CDATA[${xml.toString()}]]>")
                        }
                    }
                }
                
                def outgoingPayload = XmlUtil.serialize(outgoingRequest)
                log.debug("Outgoing payload {}", outgoingPayload);
	            return outgoingPayload;

	        } catch (Exception e) {
	            throw new TransformerException(this, e);
	        }
	 

	}
}
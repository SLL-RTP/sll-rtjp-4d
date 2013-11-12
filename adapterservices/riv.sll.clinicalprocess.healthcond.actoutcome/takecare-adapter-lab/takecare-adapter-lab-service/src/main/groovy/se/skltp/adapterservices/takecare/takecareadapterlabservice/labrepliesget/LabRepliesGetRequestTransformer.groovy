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

import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
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
                    X2Message(InvokingSystem:"${invokingSystem}", MsgType:"${REQUEST}", Time:"${new Date().format('yyyyMMddHHmmss')}") {
                        PatientId("${incomingRequest.patientId?.id}")
                        if (incomingRequest.timePeriod?.start != null) {
                            def d = incomingRequest.timePeriod?.start
                            d.padRight(8)
                            StartDate("${d[0..3]}-${d[4..5]}-${d[6..7]}")
                        }
                        if (incomingRequest.timePeriod?.end != null) {
                            def d = incomingRequest.timePeriod?.end
                            d.padRight(8)
                            EndDate("${d[0..3]}-${d[4..5]}-${d[6..7]}")

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
                      careunitid        "${incomingRequest.careUnitHSAid}"
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
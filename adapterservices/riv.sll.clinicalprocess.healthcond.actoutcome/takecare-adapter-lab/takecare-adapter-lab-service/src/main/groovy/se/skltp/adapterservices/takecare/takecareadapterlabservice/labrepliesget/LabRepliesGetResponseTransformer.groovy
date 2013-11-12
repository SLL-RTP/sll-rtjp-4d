package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import static javax.xml.bind.DatatypeConverter.parseDateTime

import org.mule.api.MuleMessage
import org.mule.api.transformer.TransformerException
import org.mule.transformer.AbstractMessageTransformer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.soitoolkit.commons.mule.jaxb.JaxbUtil

import se.skltp.adapterservices.takecare.lab.LabRepliesGetResponse

public class LabRepliesGetResponseTransformer extends AbstractMessageTransformer {

    private static final Logger log = LoggerFactory.getLogger(LabRepliesGetResponseTransformer.class)
    private static final JaxbUtil jaxbUtil_incoming = new JaxbUtil(LabRepliesGetResponse.class)

    /**
     * Message aware transformer that ...
     */
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

        // Perform any message aware processing here, otherwise delegate as much as possible to pojoTransform() for easier unit testing

        return pojoTransform(message.getPayload(), outputEncoding)
    }

    /**
     * Simple pojo transformer method that can be tested with plain unit testing...
     */
    public Object pojoTransform(Object payload, String outputEncoding) throws TransformerException {
        log.debug("Incoming payload {}", String.valueOf(payload))
        
        try {
            LabRepliesGetResponse labRepliesGetResponse = (LabRepliesGetResponse)jaxbUtil_incoming.unmarshal(payload)
            def x2message = new XmlSlurper().parseText(labRepliesGetResponse.getLabRepliesGetResult())
            if (x2message.@MsgType.text() == "Error") {
                log.debug("An error message was detected: Code=" + x2message.Error.Code.text() + ", Msg= "
                        + x2message.Error.Msg.text())
                throw new RuntimeException("resultCode: " + x2message.Error.Code.text() + " resultText: "
                + x2message.Error.Msg.text())
            }

            def patientOverView = x2message.PatientOverView
            def builder = new StreamingMarkupBuilder()
            builder.encoding = 'UTF-8'
            def response = builder.bind {
                //mkp.xmlDeclaration()
                mkp.declareNamespace(core:'urn:riv:sll:clinicalprocess:healthcond:actoutcome:1')
                mkp.declareNamespace(tns:'urn:riv:sll:clinicalprocess:healthcond:actoutcome:GetClinicalChemistryLabOrderOutcomeResponder:1')
                mkp.declareNamespace(xsi:'http://www.w3.org/2001/XMLSchema-instance')
                'tns:GetClinicalChemistryLabOrderOutcomeResponse' {
                    'tns:resultCode' 'OK'
                    //'tns:labResults' {
                        patientOverView.Units.Unit.each {
                            it.Labs2.Answer.each{
                                def answer = it
                                'tns:labResults' {
                                    answer.Analysis.each {
                                        def ana = it
                                        'tns:analysis' {
                                            'tns:instanceId' ana.InstanceId.text()
                                            'tns:code' {
                                                if (ana.Code.Code.size() != 0) {
                                                    'tns:code' ana.Code.Code.text()
                                                }
                                                if (ana.Code.DisplayName.size() != 0) {
                                                    'tns:displayName' ana.Code.DisplayName.text()
                                                }
                                            }
                                            'tns:takenDateTime' parseDateTime(ana.TakenDateTime.text()).format('yyyyMMddHHmmss')
                                            if (ana.Comment.size() != 0) {
                                                'tns:comment'       ana.Comment.text()
                                            }
                                            if (ana.SampleType.size() != 0) {
                                                'tns:sampleType'    ana.SampleType.text()
                                            }
                                            'tns:outcomeValue' {
                                                'core:value'        ana.ResultSummary.text()
                                                'core:unit'         ana.UnitOfMeasure.text()
                                            } 
                                            'tns:pathological'  ana.Pathological.text()
                                            if (ana.Reference.size() != 0) {
                                                'tns:reference'     ana.Reference.text()
                                            }
                                        }
                                    }
                                    'tns:instanceId'          answer.InstanceId.text()
                                    def regDateTime = 
                                    'tns:registeredDateTime'  parseDateTime(answer.RegisteredDateTime.text()).format('yyyyMMddHHmmss')
                                    if (answer.Comment.size() != 0) {
                                        'tns:comment'             answer.Comment.text()
                                    }
                                }
                            }
                        }
                    //}
                }
            }

            def payloadOut = XmlUtil.serialize(response)

            log.debug("Outgoing payload {}", payloadOut)

            return payloadOut
        } catch (Exception e) {
            throw new TransformerException(this, e)
        }
    }

}
package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;



public class ImportRaRegistryDataRequestTransformer extends AbstractMessageTransformer {

  private static final Logger log = LoggerFactory.getLogger(ImportRaRegistryDataRequestTransformer.class);
  static final REGISTRY = 'registry'
  static final LOCATION = 'location'
  static final SENDER = 'sender'
  static final XML = 'xml' 
  
  static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
  static final String ENCRYPTION_KEY = "SecretKey"
  



  /**
   * Transformer that transforms the payload to SOAP Envelope
   */
  @Override
  public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

    // Perform any message aware processing here, otherwise delegate as much as possible to pojoTransform() for easier unit testing

    return pojoTransform(message.getPayload(), outputEncoding);
  }

  /**
   * Transform incoming message to a SOAP Envelope 
   */
  protected Object pojoTransform(Object src, String encoding) throws TransformerException {

    log.debug("Incoming payload: {}", src);
    
    def params = src.toString().split('&').inject([:]) {map, kv->
      def (key, value) = kv.split('=').toList()
      map[key] = value != null ? URLDecoder.decode(value, 'UTF-8') : null
      map
    }  
    

    String xml = decrypt(params[XML]) 
   
    
    try    {
      def inRegistryData = new XmlSlurper().parseText(xml)
      def builder = new StreamingMarkupBuilder()
      builder.encoding = 'UTF-8'
      def envelope = builder.bind {
        mkp.xmlDeclaration()
        mkp.declareNamespace(soapenv:'http://schemas.xmlsoap.org/soap/envelope/')
        mkp.declareNamespace(core:'urn:riv:clinicalprocess:healthcond:description:1')
        mkp.declareNamespace(itr:'urn:riv:itintegration:registry:1')
        mkp.declareNamespace(tns:'urn:riv:clinicalprocess:healthcond:description:RegisterRaDSDataResponder:1')
        mkp.declareNamespace(xsi:'http://www.w3.org/2001/XMLSchema-instance')
        'soapenv:Envelope' {
          'soapenv:Header' { 'itr:LogicalAddress' 'Logisk adress' }
          'soapenv:Body' {
            'tns:RegisterRaDSData' {
              if (1 == inRegistryData.Patient.size())
                'tns:patient' {
                  def patient = inRegistryData.Patient
                  if (1 == patient.ID.size())
                    'core:patientId' patient.ID.text()
                  if (1 == patient.GivenName.size())
                    'core:givenName' patient.GivenName.text()
                  if (1 == patient.Surname.size())
                    'core:surname'   patient.Surname.text()
                }
              if (1 == inRegistryData.Data.size())
                'tns:registryData' {
                  def data = inRegistryData.Data
                  if (1 == data.InclusionCriteria.size())
                    'tns:inclusionCriteria' {
                      def ic = data.InclusionCriteria
                      if (1 == ic.RegisterRaDSDataResponse.size())
                        'tns:arthritisIn3Joints'    ic.ArthritisIn3Joints.text().toBoolean()
                      if (1 == ic.ArthritisInHand.size())
                        'tns:arthritisInHand'       ic.ArthritisInHand.text().toBoolean()
                      if (1 == ic.MorningStiffness.size())
                        'tns:morningStiffness'      ic.MorningStiffness.text().toBoolean()
                      if (1 == ic.RheumaticNoduli.size())
                        'tns:rheumaticNoduli'       ic.RheumaticNoduli.text().toBoolean()
                      if (1 == ic.RheumatoidFaktorPos.size())
                        'tns:rheumatoidFaktorPos'   ic.RheumatoidFaktorPos.text().toBoolean()
                      if (1 == ic.XrayChanges.size())
                        'tns:xrayChanges'           ic.XrayChanges.text().toBoolean()
                      if (1 == ic.SymmetricArthritis.size())
                        'tns:symmetricArthritis'    ic.SymmetricArthritis.text().toBoolean()
                    }
                  if (1 == data.ResponsiblePhysician.size())
                    'tns:responsiblePhysician' {
                      def rp = data.ResponsiblePhysician
                      if (1 == rp.ID.size())
                        'tns:ID'                    rp.ID.text()
                      if (1 == rp.GivenName.size())
                        'tns:givenName'             rp.GivenName.text()
                      if (1 == rp.Surname.size())
                        'tns:surname'               rp.Surname.text()
                      if (1 == rp.HSAID.size())
                        'tns:HSAID'                 rp.HSAID.text()
                      if (1 == rp.Profession.size())
                        'tns:profession'            rp.Profession.text()
                    }
                  if (1 == data.Hospital.size())
                    'tns:hospital' {
                      def h = data.Hospital
                      if (1 == h.Provider.size())
                        'tns:provider'              h.Provider.text()
                      if (1 == h.Unit.size())
                        'tns:unit'                  h.Unit.text()
                      if (1 == h.HSAID.size())
                        'tns:HSAID'                 h.HSAID.text()
                    }
                  if (0 < data.Diagnosis.size())
                    'tns:diagnosis' {
                      data.Diagnosis.each{
                        def d = it
                        'tns:diagnose' {
                          if (1 == d.Name.size())
                            'tns:name'              d.Name.text()
                          if (1 == d.OnsetDate.size())
                            'tns:onsetDate'         d.OnsetDate.text()
                        }
                      }
                    }
                  if (1 == data.DominantHand.size())
                    'tns:dominantHand'            data.DominantHand.text()
                  if (1 == data.Weight.size())
                    'tns:weight'                  data.Weight.text()
                  if (1 == data.Height.size())
                    'tns:height'                  data.Height.text()
                  if (1 == data.Menopaus.size())
                    'tns:menopaus'                data.Menopaus.text()
                  if (1 == data.MenopausAge.size() )
                    'tns:menopausAge'             data.MenopausAge.text()
                  if (1 == data.StudyProject.size())
                    'tns:studyProject'            data.StudyProject.text()
                  if (1 == data.VisitDate.size())
                    'tns:visitDate'               data.VisitDate.text()
                  if (1 == data.VisitDoctor.size())
                    'tns:visitDoctor' {
                      def d = data.VisitDoctor
                      if (1 == d.ID.size())
                        'tns:ID'                    d.ID.text()
                      if (1 == d.GivenName.size())
                        'tns:givenName'             d.GivenName.text()
                      if (1 == d.Surname.size())
                        'tns:surname'               d.Surname.text()
                      if (1 == d.HSAID.size())
                        'tns:HSAID'                 d.HSAID.text()
                      if (1 == d.Profession.size())
                        'tns:profession'            d.Profession.text()
                    }
                  if (1 == data.SR)
                    'tns:SR'                      data.SR.text()
                  if (1 == data.CRP.size())
                    'tns:CRP'                     data.CRP.text()
                  if (1 == data.Workability)
                    'tns:workability'             data.Workability.text()
                  if (1 == data.Pain)
                    'tns:pain'                    data.Pain.text()
                  if (1 == data.HAQ.size())
                    'tns:HAQ'                     data.HAQ.text()
                  if (1 == data.GlobalHealth.size())
                    'tns:globalHealth'            data.GlobalHealth.text()
                  if (1 == data.TenderJoints.size())
                    'tns:tenderJoints'            data.TenderJoints.text()
                  if (1 == data.SwollenJoints.size())
                    'tns:swollenJoints'           data.SwollenJoints.text()
                  if (1 == data.DAS.size())
                    'tns:DAS'                     data.DAS.text()
                  if (1 == data.DoctorsGlobal.size())
                    'tns:doctorsGlobal'           data.DoctorsGlobal.text()
                  if (1 == data.Trombocyter.size())
                    'tns:thrombocytes'            data.Trombocyter.text()
                  if (1 == data.RF.size())
                    'tns:RF'                      data.RF.text()
                  if (1 == data.AntiCcp.size())
                    'tns:antiCcp'                 data.AntiCcp.text()
                  if (1 == data.XrayDate.size())
                    'tns:xrayDate'                data.XrayDate.text()
                  if (1 == data.Erosion.size())
                    'tns:erosion'                 data.Erosion.text()
                  if (1 == data.Progress.size())
                    'tns:progress'                data.Progress.text()
                  if (0 < data.Drug.size())
                    'tns:drugs' {
                      data.Drug.each{
                        def drug = it
                        'tns:drug' {
                          if (1 == drug.Name.size())
                            'tns:name'             drug.Name.text()
                          if (1 == drug.Dose.size())
                            'tns:dose'             drug.Dose.text()
                          if (1 == drug.StartDate.size())
                            'tns:startDate'        drug.StartDate.text()
                          if (1 == drug.EndDate.size())
                            'tns:endDate'          drug.EndDate.text()
                          if (drug.EndCause.size())
                            'tns:endCause'         drug.EndCause.text()
                          if (1 == drug.Type.size())
                            'tns:type'             drug.Type.text()
                          if (drug.Interval.size())
                            'tns:interval'         drug.Interval.text()
                        }
                      }
                    }
                  if (1 == data.Smoking.size())
                    'tns:smoking'                 data.Smoking.text().toBoolean()
                  if (1 == data.TenderJoints68.size())
                    'tns:tenderJoints68'          data.TenderJoints68.text()
                  if (1 == data.SwollenJoints66.size())
                    'tns:swollenJoints66'         data.SwollenJoints66.text()
                  if (1 == data.BasdaiScore.size())
                    'tns:basdaiScore'             data.BasdaiScore.text()
                  if (1 == data.BasfiScore.size())
                    'tns:basfiScore'              data.BasfiScore.text()
                } //registryData
            } //RegisterRaDSData
          } //body
        } //envelope
      }
      def payloadOut = XmlUtil.serialize(envelope)

      log.debug("Outgoing payload [{}]", payloadOut)

      return payloadOut
    } catch (Exception e) {
      throw new TransformerException(this, e);
    }
  }
  
  private String decrypt(String encryptedData)  {
    Key key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), 'AES');
    Cipher c = Cipher.getInstance(CIPHER_TRANSFORMATION);
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = c.doFinal(decodedValue);
    String decryptedValue = new String(decValue);
    
    log.debug("Decrypted value {[]}", decryptedValue)
    return decryptedValue;
  }
}
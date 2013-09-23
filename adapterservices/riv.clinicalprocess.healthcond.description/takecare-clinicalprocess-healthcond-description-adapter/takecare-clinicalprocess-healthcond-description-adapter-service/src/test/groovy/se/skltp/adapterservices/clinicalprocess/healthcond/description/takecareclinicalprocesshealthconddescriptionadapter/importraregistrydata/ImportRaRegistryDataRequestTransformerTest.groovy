package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;



import java.awt.im.InputContext;

import groovy.xml.XmlUtil;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.soitoolkit.commons.mule.util.MiscUtil;

import se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.AesUtil;


public class ImportRaRegistryDataRequestTransformerTest extends XMLTestCase{

  @Test
  public void testPojoTransform() throws Exception {

    // Specify input and expected result
    StringBuffer input = new StringBuffer();
    input.append 'registry=rareg'
    input.append '&sender=Take%20Care'
    input.append '&location=Karolinska%20Universitetssjukhuset'
    String xml = new File('src/test/resources/testfiles/importRaRegistryData/request-input.xml').text
    input.append '&xml=' + URLEncoder.encode(xml, 'UTF-8') 
    
    String expectedResult = new File('src/test/resources/testfiles/importRaRegistryData/request-input-expected-result.xml').text

    ImportRaRegistryDataRequestTransformer uut = new ImportRaRegistryDataRequestTransformer()
    uut.encryptionKey = null
    String result = (String)uut.pojoTransform(input, null)

    def envelope = new XmlSlurper().parseText(result)

    assertTrue('Element Header is present', 1 == envelope.Header.size())
    assertTrue('Element Body is present', 1 == envelope.Body.size())
    assertTrue('PatientId is 191212121212', '191212121212' == envelope.Body.RegisterRaDSData.patient.patientId.text())
    assertTrue('We have three drug elements', 3 == envelope.Body.RegisterRaDSData.registryData.drugs.drug.size())

    XMLUnit.setIgnoreWhitespace(true)
    // is result as expected?
    assertXMLEqual("Compare expected result with actual result", expectedResult, result)

  }

  @Test
  public void testPojoTransformWithEncryption() throws Exception {

    String key = 'TheBestSecretKey'
    // Specify input and expected result
    StringBuffer input = new StringBuffer();
    input.append 'registry=rareg'
    input.append '&sender=Take%20Care'
    input.append '&location=Karolinska%20Universitetssjukhuset'
    String xml = new File('src/test/resources/testfiles/importRaRegistryData/request-input.xml').text
    AesUtil util =  new AesUtil(secretKey: key)
    String encryptedXml = util.encrypt(xml)
    input.append '&xml=' + URLEncoder.encode(encryptedXml, 'UTF-8')
    
    String expectedResult = new File('src/test/resources/testfiles/importRaRegistryData/request-input-expected-result.xml').text

    ImportRaRegistryDataRequestTransformer uut = new ImportRaRegistryDataRequestTransformer(encryptionEnabled: true, encryptionKey: key)
    String result = (String)uut.pojoTransform(input, null)

    def envelope = new XmlSlurper().parseText(result)

    assertTrue('Element Header is present', 1 == envelope.Header.size())
    assertTrue('Element Body is present', 1 == envelope.Body.size())
    assertTrue('PatientId is 191212121212', '191212121212' == envelope.Body.RegisterRaDSData.patient.patientId.text())
    assertTrue('We have three drug elements', 3 == envelope.Body.RegisterRaDSData.registryData.drugs.drug.size())

    XMLUnit.setIgnoreWhitespace(true)
    // is result as expected?
    assertXMLEqual("Compare expected result with actual result", expectedResult, result)

  }

  
  @Test
  public void testTransformerXml() throws Exception {

    // Specify input and expected result
    String input = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input.xml")
    String expectedResult = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-expected-result.xml")

    ImportRaRegistryDataRequestTransformer uut = new ImportRaRegistryDataRequestTransformer()
    String result = (String)uut.transformXml(input)

    def envelope = new XmlSlurper().parseText(result)

    assertTrue('Element Header is present', 1 == envelope.Header.size())
    assertTrue('Element Body is present', 1 == envelope.Body.size())
    assertTrue('PatientId is 191212121212', '191212121212' == envelope.Body.RegisterRaDSData.patient.patientId.text())
    assertTrue('We have three drug elements', 3 == envelope.Body.RegisterRaDSData.registryData.drugs.drug.size())

    XMLUnit.setIgnoreWhitespace(true)
    // is result as expected?
    assertXMLEqual("Compare expected result with actual result", expectedResult, result)

  }


  @Test
  public void testDecrypt() throws Exception {
    
    String key = 'TheBestSecretKey'
    AesUtil util =  new AesUtil(secretKey: key)
    String input = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input.xml")
    String encrypted = util.encrypt(input)
    
    

    ImportRaRegistryDataRequestTransformer uut = new ImportRaRegistryDataRequestTransformer(encryptionEnabled: true, encryptionKey: key)
    String result = (String)uut.decrypt(encrypted)

    XMLUnit.setIgnoreWhitespace(true)
       
    assertXMLEqual("Compare expected result with actual result", input, result)


  }
}
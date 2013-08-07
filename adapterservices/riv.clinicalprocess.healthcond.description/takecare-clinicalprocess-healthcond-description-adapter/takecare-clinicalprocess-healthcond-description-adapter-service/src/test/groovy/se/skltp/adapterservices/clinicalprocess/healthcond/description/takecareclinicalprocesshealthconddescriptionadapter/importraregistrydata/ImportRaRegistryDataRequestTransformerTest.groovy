package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;



import groovy.xml.XmlUtil;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.soitoolkit.commons.mule.util.MiscUtil;


public class ImportRaRegistryDataRequestTransformerTest extends XMLTestCase{



  @Test
  public void testTransformer_ok() throws Exception {

    // Specify input and expected result
    String input          = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input.xml")
    String expectedResult = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input-expected-result.xml")

    ImportRaRegistryDataRequestTransformer uut = new ImportRaRegistryDataRequestTransformer()
    String result = (String)uut.pojoTransform(input, "UTF-8")
    
    def envelope = new XmlSlurper().parseText(result)
    
    assertTrue('Element Header is present', 1 == envelope.Header.size())
    assertTrue('Element Body is present', 1 == envelope.Body.size())
    assertTrue('PatientId is 191212121212', '191212121212' == envelope.Body.RegisterRaDSData.patient.patientId.text())
    assertTrue('We have three drug elements', 3 == envelope.Body.RegisterRaDSData.registryData.drugs.drug.size())
    
    XMLUnit.setIgnoreWhitespace(true)
    // is result as expected?
    assertXMLEqual("Compare expected result with actual result", expectedResult, result)

  }
}
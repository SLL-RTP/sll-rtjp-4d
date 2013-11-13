package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter.importraregistrydata;

import static org.junit.Assert.assertEquals;
import static org.soitoolkit.commons.mule.smooks.SmooksUtil.runSmooksTransformer;


import org.junit.Test;
import org.soitoolkit.commons.mule.util.MiscUtil;

public class ImportRaRegistryDataResponseTransformerTest {

  @Test
  public void testTransformer_ok() throws Exception {

    // Specify input and expected result

    String input          = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/response-input.xml");

    String expectedResult = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/response-input-expected-result.txt");

    // Create the transformer under test and let it perform the transformation

    ImportRaRegistryDataResponseTransformer transformer = new ImportRaRegistryDataResponseTransformer();
    String result = (String)transformer.pojoTransform(input, "UTF-8");


    // Compare the result to the expected value
    assertEquals(expectedResult, result);
  }
}
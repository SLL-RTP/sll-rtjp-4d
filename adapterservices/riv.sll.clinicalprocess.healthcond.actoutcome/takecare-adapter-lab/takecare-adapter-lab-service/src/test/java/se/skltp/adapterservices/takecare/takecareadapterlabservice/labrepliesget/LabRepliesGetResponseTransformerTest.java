package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mule.api.transformer.TransformerException;
import org.soitoolkit.commons.mule.util.MiscUtil;

public class LabRepliesGetResponseTransformerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testTransformer_ok1() throws Exception {

        // Specify input and expected result
        File file = new File("src/test/resources/testfiles/LabRepliesGet/response-input1.xml");
        //XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        //inputFactory.setProperty("report-cdata-event", Boolean.TRUE);
        //XMLStreamReader input = inputFactory.createXMLStreamReader(new FileInputStream(
        //        file));

        String input = MiscUtil.readFileAsString("src/test/resources/testfiles/LabRepliesGet/response-input1.xml");

        String expectedResult = MiscUtil
                .readFileAsString("src/test/resources/testfiles/LabRepliesGet/response-input1-expected-result.xml");

        LabRepliesGetResponseTransformer transformer = new LabRepliesGetResponseTransformer();
        String result = (String) transformer.pojoTransform(input, "UTF-8");

        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(expectedResult, result);
    }

    @Test
    public void testTransformer_Error() throws Exception {
        thrown.expect(TransformerException.class);
        thrown.expectMessage("resultCode: 3000 resultText: Unknown error");

        String input = MiscUtil
                .readFileAsString("src/test/resources/testfiles/LabRepliesGet/response-input-error.xml");

        LabRepliesGetResponseTransformer transformer = new LabRepliesGetResponseTransformer();
        transformer.pojoTransform(input, "UTF-8");

    }
}
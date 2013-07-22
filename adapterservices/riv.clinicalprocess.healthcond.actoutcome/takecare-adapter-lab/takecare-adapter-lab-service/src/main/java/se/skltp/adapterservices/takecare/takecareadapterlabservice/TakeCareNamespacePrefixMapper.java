package se.skltp.adapterservices.takecare.takecareadapterlabservice;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * 
 */
public class TakeCareNamespacePrefixMapper extends XMLFilterImpl {

    private static final String X2MESSAGE_QNAME = "X2Message";
    private final String namespace;

    public TakeCareNamespacePrefixMapper(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(X2MESSAGE_QNAME)) {
            super.endElement(this.namespace, localName, qName);
        } else {
            super.endElement("", localName, qName);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException {
        if (qName.equals(X2MESSAGE_QNAME)) {
            super.startElement(this.namespace, localName, qName, attr);
        } else {
            super.startElement("", localName, qName, attr);
        }
    }
}
package se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget;

import static se.skltp.adapterservices.takecare.takecareadapterlabservice.TakecareAdapterLabServiceMuleServer.getAddress;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;
import org.soitoolkit.refapps.sd.sample.wsdl.v1.Fault;

import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeResponseType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.rivtabp21.GetClinicalChemistryLabOrderOutcomeResponderInterface;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.v1.DatePeriodType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.v1.PersonIdType;
import se.skltp.adapterservices.takecare.takecareadapterlabservice.TakeCareDateHelper;

public class LabRepliesGetTestConsumer {

    private static final Logger log = LoggerFactory
            .getLogger(LabRepliesGetTestConsumer.class);

    private static final RecursiveResourceBundle rb = new RecursiveResourceBundle(
            "takecare-adapter-lab-service-config");

    private GetClinicalChemistryLabOrderOutcomeResponderInterface _service = null;
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public LabRepliesGetTestConsumer(String serviceAddress) {
        
        
//        try {
//            URL url = new URL(serviceAddress + "?wsdl");
//            _service = new GetClinicalChemistryLabOrderOutcomeResponderService(url).getGetClinicalChemistryLabOrderOutcomeResponderPort();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
//        }
        
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(GetClinicalChemistryLabOrderOutcomeResponderInterface.class);
        proxyFactory.setAddress(serviceAddress);

        // Used for HTTPS
        SpringBusFactory bf = new SpringBusFactory();
        URL cxfConfig = LabRepliesGetTestConsumer.class.getClassLoader()
                .getResource("cxf-test-consumer-config.xml");
        if (cxfConfig != null) {
            proxyFactory.setBus(bf.createBus(cxfConfig));
        }

        _service = (GetClinicalChemistryLabOrderOutcomeResponderInterface) proxyFactory
                .create();
    }
    

    public static void main(String[] args) throws Fault {
        String serviceAddress = getAddress("LABREPLIESGET_INBOUND_URL");

        LabRepliesGetTestConsumer consumer = new LabRepliesGetTestConsumer(serviceAddress);
        GetClinicalChemistryLabOrderOutcomeResponseType response = consumer.callService("191212121212", sdf.format(new Date()), sdf.format(new Date()));
        log.info("Returned value = " + response.toString());
        
    }

    public GetClinicalChemistryLabOrderOutcomeResponseType callService(
            String patientId, String startDate, String endDate) throws Fault {
        log.debug("Incoming patientId {}", patientId);
        log.debug("Incoming startDate {}", startDate);
        log.debug("Incoming endDate {}", endDate);

        GetClinicalChemistryLabOrderOutcomeType request = new GetClinicalChemistryLabOrderOutcomeType();
        PersonIdType person = new PersonIdType();
        person.setId(patientId);
        person.setType("1.2.752.129.2.1.3.1"); // type = personnummer
        request.setPatientId(person);
        if (startDate != null) {
            DatePeriodType time = new DatePeriodType();
            time.setStart(startDate);
            time.setEnd(endDate);
        }

        return _service.getClinicalChemistryLabOrderOutcome("Logical Address", request);

    }

}
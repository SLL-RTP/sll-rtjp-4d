/**
 * Copyright 2013 Stockholm LŠns Landsting
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public

 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the

 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,

 *   Boston, MA 02111-1307  USA
 */
package se.sll.skltp.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcome.v1;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.sll.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcome.v1.rivtabp21.GetMicroBiologiLabOrderOutcomeResponderInterface;
import se.sll.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcomeresponder.v1.ACPAResultType;
import se.sll.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcomeresponder.v1.GetMicroBiologiLabOrderOutcomeResponseType;
import se.sll.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcomeresponder.v1.GetMicroBiologiLabOrderOutcomeType;


@WebService(serviceName = "GetMicroBiologiLabOrderOutcomeResponderService", endpointInterface = "se.sll.clinicalprocess.healthcond.actoutcome.getmicrobiologilaborderoutcome.v1.rivtabp21.GetMicroBiologiLabOrderOutcomeResponderInterface", portName = "GetMicroBiologiLabOrderOutcomeResponderPort", targetNamespace = "urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcome:1:rivtabp21", wsdlLocation = "interactions/GetMicroBiologiLabOrderOutcomeInteraction/GetMicroBiologiLabOrderOutcomeInteraction_1.0_RIVTABP21.wsdl")
public class GetMicroBiologiLabOrderOutcomeImpl implements GetMicroBiologiLabOrderOutcomeResponderInterface {

	@WebResult(name = "GetMicroBiologiLabOrderOutcomeResponse", targetNamespace = "urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcomeResponder:1", partName = "parameters")
	@WebMethod(operationName = "GetMicroBiologiLabOrderOutcome", action = "urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcomeResponder:1:GetMicroBiologiLabOrderOutcome")
	public GetMicroBiologiLabOrderOutcomeResponseType getMicroBiologiLabOrderOutcome(
			@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String arg0,
			@WebParam(partName = "parameters", name = "GetMicroBiologiLabOrderOutcome", targetNamespace = "urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcomeResponder:1") GetMicroBiologiLabOrderOutcomeType arg1) {		
		
		GetMicroBiologiLabOrderOutcomeResponseType result = new GetMicroBiologiLabOrderOutcomeResponseType();

		StringBuffer comment = new StringBuffer("Test result for ");

		if (arg1 != null) {
			comment.append("[" + arg1.getPatientId() + "]");

			ACPAResultType acpa = new ACPAResultType();
			
			if (randInt(1, 2) == 1) {
			  acpa.setACPA(false);
			} else {
			  acpa.setACPA(true); 
			}
			
			acpa.setDateOfSample(convertToXmlDate(randomDate()));
			acpa.setCcp(randInt(12, 199)/10);
			acpa.setCitC1Igg(randInt(12, 190)/10);
			acpa.setCitEnoIgg(randInt(12, 190)/10);
			acpa.setCitFibIgg(randInt(12, 190)/10);
			result.setACPAResult(acpa);
		}
		
		result.setComment(comment.toString());
		return result;
	}
	
	
	private Date randomDate() {
	  
	  final GregorianCalendar gc = new GregorianCalendar();

    final int year = randInt(1990, 2010);

    gc.set(Calendar.YEAR, year);

    final int dayOfYear = randInt(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

    gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
    
    return gc.getTime();
	}
	
	private int randInt(int min, int max) {

    // Usually this can be a field rather than a method variable
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}
	private XMLGregorianCalendar convertToXmlDate(Date myDate) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(myDate);
		XMLGregorianCalendar date = null;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}

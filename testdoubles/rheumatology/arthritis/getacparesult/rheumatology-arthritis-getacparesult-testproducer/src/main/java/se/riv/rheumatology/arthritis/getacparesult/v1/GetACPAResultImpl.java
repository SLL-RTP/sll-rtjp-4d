/**
 * Copyright 2013 Stockholm Läns Landsting
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
package se.riv.rheumatology.arthritis.getacparesult.v1;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import se.riv.rheumatology.arthritis.getacparesult.v1.rivtabp21.GetACPAResultResponderInterface;
import se.riv.rheumatology.arthritis.getacparesultresponder.v1.ACPAResultType;
import se.riv.rheumatology.arthritis.getacparesultresponder.v1.GetACPAResultResponseType;
import se.riv.rheumatology.arthritis.getacparesultresponder.v1.GetACPAResultType;

@WebService(serviceName = "GetACPAResultResponderService", endpointInterface = "se.riv.rheumatology.arthritis.getacparesult.v1.rivtabp21.GetACPAResultResponderInterface", portName = "GetACPAResultResponderPort", targetNamespace = "urn:riv:rheumatology:arthritis:GetACPAResult:1:rivtabp21", wsdlLocation = "interactions/GetACPAResultInteraction/GetACPAResultInteraction_1.0_RIVTABP21.wsdl")
public class GetACPAResultImpl implements GetACPAResultResponderInterface {

	@WebResult(name = "GetACPAResultResponse", targetNamespace = "urn:riv:rheumatology:arthritis:GetACPAResultResponder:1", partName = "parameters")
	@WebMethod(operationName = "GetACPAResult", action = "urn:riv:rheumatology:arthritis:GetACPAResultResponder:1:GetACPAResult")
	public GetACPAResultResponseType getACPAResult(
			@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String arg0,
			@WebParam(partName = "parameters", name = "GetACPAResult", targetNamespace = "urn:riv:rheumatology:arthritis:GetACPAResultResponder:1") GetACPAResultType arg1) {

		GetACPAResultResponseType result = new GetACPAResultResponseType();

		StringBuffer comment = new StringBuffer("Test result for ");

		if (arg1 != null) {
			comment.append("[" + arg1.getPatientId() + "]");

			ACPAResultType acpa = new ACPAResultType();
			acpa.setACPA(false);
			
			acpa.setDateOfSample(convertToXmlDate(new Date()));
			acpa.setCcp(12.1f);
			acpa.setCitC1Igg(13.2f);
			acpa.setCitEnoIgg(14.3f);
			acpa.setCitFibIgg(15.4f);
			result.setACPAResult(acpa);
		}
		
		result.setComment(comment.toString());
		return result;
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

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
package se.sll.skltp.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcome.v1;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;



import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.CodeType;
import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeResponseType;
import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultType;
import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultType.Analysis;
import se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.rivtabp21.GetClinicalChemistryLabOrderOutcomeResponderInterface;

@WebService(serviceName = "GetClinicalChemistryLabOrderOutcomeResponderService", 
			endpointInterface = "se.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.rivtabp21.GetClinicalChemistryLabOrderOutcomeResponderInterface", 
			portName = "GetClinicalChemistryLabOrderOutcomeResponderPort", targetNamespace = "urn:sll:clinicalprocess:healthcond:actoutcome:GetClinicalChemistryLabOrderOutcome:1:rivtabp21", 
			wsdlLocation = "interactions/GetClinicalChemistryLabOrderOutcomeInteraction/GetClinicalChemistryLabOrderOutcomeInteraction_1.0_RIVTABP21.wsdl")
public class GetClinicalChemistryLabOrderOutcomeImpl implements GetClinicalChemistryLabOrderOutcomeResponderInterface {

	public GetClinicalChemistryLabOrderOutcomeResponseType getClinicalChemistryLabOrderOutcome(String arg0,
			GetClinicalChemistryLabOrderOutcomeType arg1) {
		GetClinicalChemistryLabOrderOutcomeResponseType response = new GetClinicalChemistryLabOrderOutcomeResponseType();
		StringBuffer comment = new StringBuffer("Test result for ");

		if (arg1 != null && arg1.getPatientId() != null) {
			comment.append("[HealthcareFacilityId=" + arg1.getHealthcareFacilityId() +", PatientId=" + arg1.getPatientId() + "]");
		}

		List<LabResultType> labResults = response.getLabResults();

		LabResultType labResult = null;
		// labresult #1
		{
			labResult = new LabResultType();
			labResult.setInstanceId("id 1");
			labResult.setRegisteredDateTime(convertToXmlDate(new DateTime(
					"2012-12-13T21:39:45.618+02:00").toDate()));
			labResult.setComment("Comment for labresult #1");
			List<Analysis> analysis = labResult.getAnalysis();
			{
				// analys #1
				Analysis analys = new Analysis();
				analys.setInstanceId("id 1");
				CodeType code = new CodeType();
				code.setCode("code A");
				code.setDisplayName("DisplayName code 1A");
				analys.setCode(code);
				analys.setTakenDateTime(convertToXmlDate(new DateTime(
						"2012-12-13T21:39:45.618+02:00").toDate()));
				analys.setComment("analysis #1 comment");
				analys.setSampleType("sample type A");
				analys.setResultSummary("result summary");
				analys.setUnitOfMeasure("mm");
				analys.setPathological(true);
				analysis.add(analys);
			}
			{
				// analys #2
				Analysis analys = new Analysis();
				analys.setInstanceId("id 2");
				CodeType code = new CodeType();
				code.setCode("code B");
				code.setDisplayName("DisplayName code 1B");
				analys.setCode(code);
				analys.setTakenDateTime(convertToXmlDate(new DateTime(
						"2012-12-14T21:39:45.618+02:00").toDate()));
				analys.setComment("analysis #2 comment");
				analys.setSampleType("sample type AB");
				analys.setResultSummary("result summary");
				analys.setUnitOfMeasure("cm");
				analys.setPathological(false);
				analys.setReference("reference 7");
				analysis.add(analys);
			}
		}
		labResults.add(labResult);

		// labresult #2
		{
			labResult = new LabResultType();
			labResult.setInstanceId("id 2");
			labResult.setRegisteredDateTime(convertToXmlDate(new DateTime(
					"2012-12-13T21:39:45.618+02:00").toDate()));
			labResult.setComment("Comment for labresult #2");
			List<Analysis> analysis = labResult.getAnalysis();
			{
				// analys #1
				Analysis analys = new Analysis();
				analys.setInstanceId("id 1");
				CodeType code = new CodeType();
				code.setCode("code A");
				code.setDisplayName("DisplayName code 2A");
				analys.setCode(code);
				analys.setTakenDateTime(convertToXmlDate(new DateTime(
						"2012-12-13T21:39:45.618+02:00").toDate()));
				analys.setComment("analysis #1 comment");
				analys.setSampleType("sample type A");
				analys.setResultSummary("result summary");
				analys.setUnitOfMeasure("mm");
				analys.setPathological(true);
				analysis.add(analys);
			}
			{
				// analys #2
				Analysis analys = new Analysis();
				analys.setInstanceId("id 2");
				CodeType code = new CodeType();
				code.setCode("code B");
				code.setDisplayName("DisplayName code 2B");
				analys.setCode(code);
				analys.setTakenDateTime(convertToXmlDate(new DateTime(
						"2012-12-14T21:39:45.618+02:00").toDate()));
				analys.setComment("analysis #2 comment");
				analys.setSampleType("sample type AB");
				analys.setResultSummary("result summary");
				analys.setUnitOfMeasure("cm");
				analys.setPathological(false);
				analys.setReference("reference 7");
				analysis.add(analys);
			}
		}
		labResults.add(labResult);

		response.setComment(comment.toString());
		return response;
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

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
package se.skltp.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcome.v1;

import java.util.List;

import javax.jws.WebService;

import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.CodeType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeResponseType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultsType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultsType.Analysis;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.ResultCodeEnum;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.rivtabp21.GetClinicalChemistryLabOrderOutcomeResponderInterface;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.v1.PQType;

@WebService
public class GetClinicalChemistryLabOrderOutcomeImpl implements
        GetClinicalChemistryLabOrderOutcomeResponderInterface {

    public GetClinicalChemistryLabOrderOutcomeResponseType getClinicalChemistryLabOrderOutcome(
            String arg0, GetClinicalChemistryLabOrderOutcomeType arg1) {
        GetClinicalChemistryLabOrderOutcomeResponseType response = new GetClinicalChemistryLabOrderOutcomeResponseType();
        StringBuffer comment = new StringBuffer("Test result for ");

        if (arg1 != null && arg1.getPatientId() != null) {
            comment.append("[CareUnitHSAid=" + arg1.getCareUnitHSAid()
                    + ", PatientId=" + arg1.getPatientId().getId() + "]");
        }

        List<LabResultsType> labResults = response.getLabResults();

        LabResultsType labResult = null;
        // labresult #1
        {
            labResult = new LabResultsType();
            labResult.setInstanceId("id 1");
            labResult.setRegisteredDateTime("20121213213945");
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
                analys.setTakenDateTime("20121213213945");
                analys.setComment("analysis #1 comment");
                analys.setSampleType("sample type A");
                PQType value = new PQType();
                value.setValue(33.4);
                value.setUnit("mg");
                analys.setOutcomeValue(value);
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
                analys.setTakenDateTime("20121214213945");
                analys.setComment("analysis #2 comment");
                analys.setSampleType("sample type AB");
                PQType value = new PQType();
                value.setValue(12.7);
                value.setUnit("ml");
                analys.setPathological(false);
                analys.setReference("reference 7");
                analysis.add(analys);
            }
        }
        labResults.add(labResult);

        // labresult #2
        {
            labResult = new LabResultsType();
            labResult.setInstanceId("id 2");
            labResult.setRegisteredDateTime("20121213213945");
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
                analys.setTakenDateTime("20121213213945");
                analys.setComment("analysis #1 comment");
                analys.setSampleType("sample type A");
                PQType value = new PQType();
                value.setValue(19.2);
                value.setUnit("cm");
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
                analys.setTakenDateTime("20121214213945");
                analys.setComment("analysis #2 comment");
                analys.setSampleType("sample type AB");
                PQType value = new PQType();
                value.setValue(5.4);
                value.setUnit("mg");
                analys.setPathological(false);
                analys.setReference("reference 7");
                analysis.add(analys);
            }
        }
        labResults.add(labResult);

        response.setComment(comment.toString());
        response.setResultCode(ResultCodeEnum.OK);
        return response;
    }

}

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

import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.AnalysisType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.CodeType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeResponseType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.GetClinicalChemistryLabOrderOutcomeType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultsType;
import se.riv.sll.clinicalprocess.healthcond.actoutcome.getclinicalchemistrylaborderoutcomeresponder.v1.LabResultsType.Analyses;
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
            Analyses analyses = new Analyses();
            labResult.setAnalyses(analyses);
            {
                // analysis #1
                AnalysisType analysis = new AnalysisType();
                analysis.setInstanceId("id 1");
                CodeType code = new CodeType();
                code.setCode("code A");
                code.setDisplayName("DisplayName code 1A");
                analysis.setCode(code);
                analysis.setTakenDateTime("20121213213945");
                analysis.setComment("analysis #1 comment");
                analysis.setSampleType("sample type A");
                PQType value = new PQType();
                value.setValue(33.4);
                value.setUnit("mg");
                analysis.setOutcomeValue(value);
                analysis.setPathological(true);
                analyses.getAnalysis().add(analysis);
            }
            {
                // analysis #2
                AnalysisType analysis = new AnalysisType();
                analysis.setInstanceId("id 2");
                CodeType code = new CodeType();
                code.setCode("code B");
                code.setDisplayName("DisplayName code 1B");
                analysis.setCode(code);
                analysis.setTakenDateTime("20121214213945");
                analysis.setComment("analysis #2 comment");
                analysis.setSampleType("sample type AB");
                PQType value = new PQType();
                value.setValue(12.7);
                value.setUnit("ml");
                analysis.setPathological(false);
                analysis.setReference("reference 7");
                analyses.getAnalysis().add(analysis);
            }
        }
        labResults.add(labResult);

        // labresult #2
        {
            labResult = new LabResultsType();
            labResult.setInstanceId("id 2");
            labResult.setRegisteredDateTime("20121213213945");
            labResult.setComment("Comment for labresult #2");
            Analyses analyses = new Analyses();
            labResult.setAnalyses(analyses);
            {
                // analysis #1
                AnalysisType analysis = new AnalysisType();
                analysis.setInstanceId("id 1");
                CodeType code = new CodeType();
                code.setCode("code A");
                code.setDisplayName("DisplayName code 2A");
                analysis.setCode(code);
                analysis.setTakenDateTime("20121213213945");
                analysis.setComment("analysis #1 comment");
                analysis.setSampleType("sample type A");
                PQType value = new PQType();
                value.setValue(19.2);
                value.setUnit("cm");
                analysis.setPathological(true);
                analyses.getAnalysis().add(analysis);
            }
            {
                // analysis #2
                AnalysisType analysis = new AnalysisType();
                analysis.setInstanceId("id 2");
                CodeType code = new CodeType();
                code.setCode("code B");
                code.setDisplayName("DisplayName code 2B");
                analysis.setCode(code);
                analysis.setTakenDateTime("20121214213945");
                analysis.setComment("analysis #2 comment");
                analysis.setSampleType("sample type AB");
                PQType value = new PQType();
                value.setValue(5.4);
                value.setUnit("mg");
                analysis.setPathological(false);
                analysis.setReference("reference 7");
                analyses.getAnalysis().add(analysis);
            }
        }
        labResults.add(labResult);

        return response;
    }

}

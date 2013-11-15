/**
 * Copyright 2013 Stockholm Läns Landsting
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *
 *   Boston, MA 02111-1307  USA
 */
package se.skltp.sll.clinicalprocess.healthcond.description.getradsdata.v1;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import se.sll.clinicalprocess.healthcond.description.getradsdata.v1.rivtabp21.GetRaDSDataResponderInterface;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.DiagnosisType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.DrugType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.GetRaDSDataResponseType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.GetRaDSDataType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.InclusionCriteriaType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.LocationType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.PersonType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.RegistryDataType;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.RegistryDataType.Diagnoses;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.RegistryDataType.Drugs;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.RegistryDataType.StudyProjects;
import se.sll.clinicalprocess.healthcond.description.getradsdataresponder.v1.StudyProjectType;

//@WebService(serviceName = "GetRaDSDataResponderService", endpointInterface = "se.riv.clinicalprocess.healthcond.description.getradsdataresponder.v1.rivtabp21.GetRaDSDataResponderInterface", portName = "GetRaDSDataResponderPort", targetNamespace = "urn:sll:clinicalprocess:healthcond:description:GetRaDSData:1:rivtabp21", wsdlLocation = "interactions/GetRaDSDataInteraction/GetRaDSDataInteraction_1.0_RIVTABP21.wsdl")
@WebService
public class GetRaDSDataImpl implements GetRaDSDataResponderInterface {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public GetRaDSDataResponseType getRaDSData(String arg0, GetRaDSDataType arg1) {

        Random r = new Random();

        GetRaDSDataResponseType result = new GetRaDSDataResponseType();
        RegistryDataType data = new RegistryDataType();
        StringBuffer comment = new StringBuffer("Test result for ");

        assert arg1 != null;

        comment.append("[" + arg1.getPatientId() + "]");

        data.setAntiCcp(randBigInt(1, 10));
        data.setBasdaiScore((float) randInt(7, 190) / 10);
        data.setBasfiScore((float) randInt(7, 190) / 10);
        data.setCRP((float) randInt(7, 190) / 10);
        data.setDAS((float) randInt(7, 190) / 10);
        data.setDiagnoses(produceDiagnosis());
        data.setDoctorsGlobal(randBigInt(1, 100));
        data.setDominantHand("right");
        Drugs drugs = new Drugs();
        drugs.getDrug().addAll(produceRandomDrugs());
        data.setDrugs(drugs);
        data.setErosion(randBigInt(1, 10));
        data.setGlobalHealth(randBigInt(1, 100));
        data.setHAQ((float) randInt(7, 190) / 10);
        data.setHeight((float) randInt(1650, 2000) / 10);

        LocationType loc = new LocationType();
        loc.setHSAId("HSAId-" + randInt(500, 4000));
        loc.setProvider("Provider-" + randInt(500, 4000));
        loc.setUnit("Enhet-" + randInt(500, 4000));
        data.setHospital(loc);
        data.setInclusionCriteria(produceRandomInclutionCriteria());
        data.setMenopaus(r.nextBoolean());
        data.setMenopausAge(randBigInt(35, 55));
        data.setPain(randBigInt(1, 100));
        data.setProgress(randBigInt(1, 100));
        data.setResponsiblePhysician(produceRandomPhysician());
        data.setRF("RF value");
        data.setSmoking(r.nextBoolean());
        data.setSR((float) randInt(7, 190) / 10);
        StudyProjects studyProjects = new StudyProjects();
        studyProjects.getStudyProject().addAll(produceRandomStudyProjects());
        data.setSwollenJoints(randBigInt(1, 28));
        data.setSwollenJoints66(randBigInt(1, 28));
        data.setTenderJoints(randBigInt(1, 28));
        data.setTenderJoints68(randBigInt(1, 28));
        data.setTrombocyter(randBigInt(1, 28));
        data.setVisitDate(sdf.format(new Date()));
        data.setVisitDoctor(produceRandomPhysician());
        data.setWeight((float) randInt(550, 900) / 10);
        data.setWorkability(randBigInt(1, 100));
        data.setXrayDate(sdf.format(new Date()));
        result.setRegistryData(data);

        return result;
    }

    private Diagnoses produceDiagnosis() {
        Diagnoses result = new Diagnoses();

        List<DiagnosisType> list = result.getDiagnosis();
        int iter = randInt(1, 5);
        for (int i = 0; i < iter; i++) {
            DiagnosisType d = new DiagnosisType();
            d.setName("Diagnose-" + randInt(1, 500));
            d.setICD10("ICD10-" + randInt(1, 50));
            d.setOnsetDate(sdf.format(new Date()));
            list.add(d);
        }
        return result;
    }

    private List<StudyProjectType> produceRandomStudyProjects() {
        List<StudyProjectType> result = new ArrayList<StudyProjectType>();

        StudyProjectType project = null;
        int iter = randInt(1, 5);
        for (int i = 0; i < iter; i++) {
            project = new StudyProjectType();
            project.setName("Project-" + i);
        }
        return result;
    }

    private PersonType produceRandomPhysician() {

        PersonType p = new PersonType();
        p.setGivenName("Bengt");
        p.setSurname("Bengtsson");
        p.setHSAId("HSAId-" + randInt(50, 5000));
        p.setID("id-" + randInt(50, 500));
        p.setProfession("Doctor");

        return p;
    }

    private InclusionCriteriaType produceRandomInclutionCriteria() {

        Random r = new Random();

        InclusionCriteriaType result = new InclusionCriteriaType();
        result.setArthritisIn3Joints(r.nextBoolean());
        result.setArthritisInHand(r.nextBoolean());
        result.setMorningStiffness(r.nextBoolean());
        result.setRheumaticNoduli(r.nextBoolean());
        result.setRheumatoidFaktorPos(r.nextBoolean());
        result.setSymmetricArthritis(r.nextBoolean());
        result.setXrayChanges(r.nextBoolean());

        return result;
    }

    private List<DrugType> produceRandomDrugs() {
        List<DrugType> result = new ArrayList<DrugType>();

        int iter = randInt(1, 5);
        for (int i = 0; i < iter; i++) {
            DrugType drug = new DrugType();
            drug.setDose((float) randInt(7, 190) / 10);
            drug.setEndCause("endcause" + randInt(10, 300));
            drug.setStartDate(sdf.format(new Date()));
            drug.setEndDate(sdf.format(new Date()));
            drug.setName("A name - " + randInt(10, 500));
            drug.setType("A type - " + randInt(10, 50));
            result.add(drug);
        }
        return result;
    }

    private int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private BigInteger randBigInt(int min, int max) {
        return BigInteger.valueOf(randInt(min, max));
    }

}

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
package se.skltp.sll.clinicalprocess.healthcond.description.getrapatientdata.v1;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import se.sll.clinicalprocess.healthcond.description.getrapatientdata.v1.rivtabp21.GetRaPatientDataResponderInterface;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.DoctorGlobalEnum;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.DoctorVariables;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.DrugType;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.GetRaPatientDataResponseType;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.GetRaPatientDataType;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.LabVariables;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.PatientDataType;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.PatientDataType.Drugs;
import se.sll.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.PatientVariables;

//@WebService(serviceName = "GetRaPatientDataResponderService", endpointInterface = "se.riv.clinicalprocess.healthcond.description.getrapatientdataresponder.v1.rivtabp21.GetRaPatientDataResponderInterface", portName = "GetRaPatientDataResponderPort", targetNamespace = "urn:sll:clinicalprocess:healthcond:description:GetRaPatientData:1:rivtabp21", wsdlLocation = "interactions/GetRaPatientDataInteraction/GetRaPatientDataInteraction_1.0_RIVTABP21.wsdl")
@WebService
public class GetRaPatientDataImpl implements GetRaPatientDataResponderInterface {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public GetRaPatientDataResponseType getRaPatientData(String arg0, GetRaPatientDataType arg1) {

        GetRaPatientDataResponseType response = new GetRaPatientDataResponseType();
        List<PatientDataType> result = response.getPatientData();
        StringBuffer comment = new StringBuffer("Test result for ");

        if (arg1 != null) {
            comment.append("[" + arg1.getPatientId().getId() + "]");

            int iter = randInt(1, 10);
            for (int i = 0; i < iter; i++) {
                result.add(produceRandomPatientData());
            }

        }

        return response;
    }

    private PatientDataType produceRandomPatientData() {
        PatientDataType data = new PatientDataType();
        data.setDate("20131001");
        PatientVariables p = new PatientVariables();
        p.setWorkability(randBigInt(0, 5));
        p.setGlobalHealth(randBigInt(0, 5));
        p.setPain(randBigInt(0, 5));
        p.setEq5D((double) randInt(7, 190) / 10);
        p.setHaq((double) randInt(7, 190) / 10);
        p.setTenderJoints28(randBigInt(0, 5));
        p.setSwollenJoints28(randBigInt(0, 5));
        data.setPatientVariables(p);

        DoctorVariables d = new DoctorVariables();
        d.setDoctorGlobal(DoctorGlobalEnum.values()[randInt(0, DoctorGlobalEnum.values().length - 1)]);
        d.setTenderJoints28(randBigInt(0, 5));
        d.setSwollenJoints28(randBigInt(0, 5));
        d.setDAS28((float) randInt(7, 199) / 10);
        d.setDAS28CRP((float) randInt(7, 199) / 10);
        data.setDoctorsVariables(d);

        LabVariables l = new LabVariables();
        l.setSR((float) randInt(7, 199));
        l.setCRP((float) randInt(7, 199));
        data.setLabVariables(l);

        data.setDrugs(produceRandomDrugs());

        return data;
    }

    private Drugs produceRandomDrugs() {
        Drugs drugs = new Drugs();

        List<DrugType> list = drugs.getDrug();
        int iter = randInt(1, 5);
        for (int i = 0; i < iter; i++) {
            DrugType drug = new DrugType();
            drug.setDose((float) randInt(7, 190) / 10);
            drug.setName("name-" + i);
            drug.setEndCause("endcause-" + i);
            drug.setInterval(Integer.toString(i));
            drug.setType("type-" + i);
            drug.setStartDate(sdf.format(new Date()));
            drug.setEndDate(sdf.format(new Date()));
            list.add(drug);
        }
        return drugs;
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

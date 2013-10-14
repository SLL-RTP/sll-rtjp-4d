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
package se.sll.skltp.clinicalprocess.healthcond.description.registerrapatientdata.v1;

import javax.jws.WebService;

import se.sll.clinicalprocess.healthcond.description.registerrapatientdata.v1.rivtabp21.RegisterRaPatientDataResponderInterface;
import se.sll.clinicalprocess.healthcond.description.registerrapatientdataresponder.v1.RegisterRaPatientDataResponseType;
import se.sll.clinicalprocess.healthcond.description.registerrapatientdataresponder.v1.RegisterRaPatientDataType;
import se.sll.clinicalprocess.healthcond.description.registerrapatientdataresponder.v1.ResultCodeEnum;

//@WebService(serviceName = "RegisterRaPatientDataResponderService", endpointInterface = "se.riv.clinicalprocess.healthcond.description.registerrapatientdataresponder.v1.rivtabp21.RegisterRaPatientDataResponderInterface", portName = "RegisterRaPatientDataResponderPort", targetNamespace = "urn:sll:clinicalprocess:healthcond:description:RegisterRaPatientData:1:rivtabp21", wsdlLocation = "interactions/RegisterRaPatientDataInteraction/RegisterRaPatientDataInteraction_1.0_RIVTABP21.wsdl")
@WebService   
public class RegisterRaPatientDataImpl implements RegisterRaPatientDataResponderInterface {

    @Override
    public RegisterRaPatientDataResponseType registerRaPatientData(String arg0,
            RegisterRaPatientDataType arg1) {
        
        RegisterRaPatientDataResponseType response = new RegisterRaPatientDataResponseType();
        response.setResultCode(ResultCodeEnum.INFO);
        String comment = "Not yet implemented";       
        response.setComment(comment);
        
        return response;
    }

}

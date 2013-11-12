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
package se.skltp.sll.clinicalprocess.healthcond.description.registerradsdata.v1;

import javax.jws.WebService;

import se.sll.clinicalprocess.healthcond.description.registerradsdata.v1.rivtabp21.RegisterRaDSDataResponderInterface;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataResponseType;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.RegisterRaDSDataType;
import se.sll.clinicalprocess.healthcond.description.registerradsdataresponder.v1.ResultCodeEnum;

//@WebService(serviceName = "RegisterRaDSDataResponderService", endpointInterface = "se.riv.clinicalprocess.healthcond.description.registerradsdataresponder.v1.rivtabp21.RegisterRaDSDataResponderInterface", portName = "RegisterRaDSDataResponderPort", targetNamespace = "urn:riv:clinicalprocess:healthcond:description:RegisterRaDSData:1:rivtabp21", wsdlLocation = "interactions/RegisterRaDSDataInteraction/RegisterRaDSDataInteraction_1.0_RIVTABP21.wsdl")
@WebService   
public class RegisterRaDSDataImpl implements RegisterRaDSDataResponderInterface {

    @Override
    public RegisterRaDSDataResponseType registerRaDSData(String arg0,
            RegisterRaDSDataType arg1) {
        
        RegisterRaDSDataResponseType response = new RegisterRaDSDataResponseType();
        String comment = "Test result for " + arg1.getPatient().getPersonId().getId();        
        response.setComment(comment);
        response.setResultCode(ResultCodeEnum.OK);
        
        return response;
    }

}

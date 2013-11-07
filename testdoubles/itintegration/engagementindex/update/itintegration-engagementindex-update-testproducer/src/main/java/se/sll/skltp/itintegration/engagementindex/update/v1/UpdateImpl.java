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
package se.sll.skltp.itintegration.engagementindex.update.v1;

import javax.jws.WebService;

import riv.itintegration.engagementindex._1.ResultCodeEnum;
import se.riv.itintegration.engagementindex.update.v1.rivtabp21.UpdateResponderInterface;
import se.riv.itintegration.engagementindex.updateresponder.v1.UpdateResponseType;
import se.riv.itintegration.engagementindex.updateresponder.v1.UpdateType;


@WebService   
public class UpdateImpl implements UpdateResponderInterface {

    @Override
    public UpdateResponseType update(String arg0, UpdateType arg1) {
        
        UpdateResponseType response = new UpdateResponseType();
        response.setResultCode(ResultCodeEnum.INFO);
        String comment = "Thanks for the update. This information will NOT be passed along to subscribers because I'm just a test stub.";       
        response.setComment(comment);
        
        return response;
    }


}

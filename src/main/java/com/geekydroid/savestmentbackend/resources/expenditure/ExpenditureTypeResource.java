package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeRequest;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureTypeService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;
import org.jboss.logging.annotations.Pos;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/expendituretype")
public class ExpenditureTypeResource {

    @Inject
    ExpenditureTypeService expenditureTypeService;

    @POST()
    public Response AddExpenditureTypes(List<ExpenditureTypeRequest> expenditureTypes) {
        return ResponseUtil.getResponseFromResult(expenditureTypeService.addExpenditureType(expenditureTypes));
    }

}

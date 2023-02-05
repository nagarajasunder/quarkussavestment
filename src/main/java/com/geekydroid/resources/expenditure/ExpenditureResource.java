package com.geekydroid.resources.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;
import com.geekydroid.domain.expenditure.ExpenditureCategory;
import com.geekydroid.domain.expenditure.ExpenditureRequest;
import com.geekydroid.service.expenditure.ExpenditureCategoryService;
import com.geekydroid.service.expenditure.ExpenditureService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/expenditure")
public class ExpenditureResource {

    @Inject
    ExpenditureCategoryService expenditureCategoryService;

    @Inject
    ExpenditureService expenditureService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewExpenditure(ExpenditureRequest request) {
        String expenditureCategoryStr = request.getExpenditureCategory();
        ExpenditureCategory expenditureCategory = expenditureCategoryService.getExpenditureCategoryByName(expenditureCategoryStr);
        if (expenditureCategory == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        ExpenditureRequest expenditureRequest = expenditureService.createExpenditure(request, expenditureCategory);

        return Response.status(Response.Status.CREATED)
                .entity(expenditureRequest).build();
    }

}

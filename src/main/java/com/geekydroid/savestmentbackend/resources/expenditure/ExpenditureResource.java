package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureOverview;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureRequest;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureCategoryService;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureService;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        NetworkResponse response = expenditureService.createExpenditure(request, expenditureCategory);

        return ResponseUtil.getResponseFromResult(response);
    }

    @PUT()
    @Path("/updateExpenditure/{expNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExpenditure(@PathParam(value = "expNumber") String expNumber, ExpenditureRequest expenditureRequest) {
        NetworkResponse response = expenditureService.updateExpenditure(expNumber, expenditureRequest);
        System.out.println(response.toString());
        return ResponseUtil.getResponseFromResult(response);
    }

    @DELETE()
    @Path("/deleteExpenditure/{expNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExpenditure(@PathParam(value = "expNumber") String expNumber) {
        NetworkResponse response = expenditureService.deleteExpenditure(expNumber);
        return ResponseUtil.getResponseFromResult(response);
    }

    @GET()
    @Path("/overview")
    @Produces(MediaType.APPLICATION_JSON)
    public ExpenditureOverview getExpenditureOverview(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate
    ) {
        expenditureService.getExpenditureOverview(startDate,endDate);
        return null;
    }

}

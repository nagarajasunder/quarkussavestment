package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureCategoryService;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.geekydroid.savestmentbackend.utils.Constants.USER_ID_HEADER_PARAM_KEY;

@Path("/expenditure")
public class ExpenditureResource {

    @Inject
    ExpenditureCategoryService expenditureCategoryService;

    @Inject
    ExpenditureService expenditureService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewExpenditure(ExpenditureRequest request, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        String expenditureCategoryStr = request.getExpenditureCategory();
        ExpenditureCategory expenditureCategory = expenditureCategoryService.getExpenditureCategoryByName(expenditureCategoryStr);
        if (expenditureCategory == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        NetworkResponse response = expenditureService.createExpenditure(userId, request, expenditureCategory);

        return ResponseUtil.getResponseFromResult(response);
    }

    @PUT()
    @Path("/updateExpenditure/{expNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExpenditure(@PathParam(value = "expNumber") String expNumber, ExpenditureRequest expenditureRequest) {
        NetworkResponse response = expenditureService.updateExpenditure(expNumber, expenditureRequest);
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
            @QueryParam("endDate") String endDate,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        return expenditureService.getExpenditureOverview(userId,startDate, endDate);
    }

    @GET()
    public Response getExpenditureByExpenditureNumber(@QueryParam("expNumber") String expNumber) {
        return ResponseUtil.getResponseFromResult(expenditureService.getExpenditureByExpNumber(expNumber));
    }

    @POST()
    @Path("/filterBy")
    public List<ExpenditureItem> getExpenditureBasedOnFilters(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId,
            ExpenditureFilterRequest request
    ) {
        return expenditureService.getExpenditureItemBasedOnGivenFilters(userId,request);
    }

    @GET
    @Path("/categoryWise")
    public Response getCategoryWiseExpense(@QueryParam("start_date") String startDate, @QueryParam("end_date") String endDate, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            return ResponseUtil.getResponseFromResult(new Error(Response.Status.BAD_REQUEST, new BadRequestException("Start date and End date should not be empty!"), null));
        }
        return ResponseUtil.getResponseFromResult(expenditureService.getCategoryWiseExpenseByGivenDateRange(startDate, endDate, userId));
    }

    @POST()
    @Path("/exportData")
    public Response exportData(
            ExpenditureFilterRequest request,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) throws IOException {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Expenditure Request cannot be null").build();
        }

        File file = expenditureService.exportDataToExcel(request,userId);
        if (file == null)
        {
            return Response.serverError().build();
        }
        Response.ResponseBuilder builder = Response.ok(file);
        builder.header("Content-Disposition","attachment; filename="+file.getName());
        file.deleteOnExit();
        return builder.build();
    }
}

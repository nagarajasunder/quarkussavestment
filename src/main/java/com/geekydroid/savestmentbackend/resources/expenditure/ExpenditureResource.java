package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;
import com.geekydroid.savestmentbackend.utils.models.Error;

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
    ExpenditureService expenditureService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ExpenditureRequest request, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        request.setCreatedBy(userId);
        ExpenditureResponse response = expenditureService.create(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT()
    @Path("/{expNumber}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam(value = "expNumber") String expNumber, ExpenditureRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        request.setExpenditureNumber(expNumber);
        ExpenditureResponse response = expenditureService.update(request);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @DELETE()
    @Path("/{expNumber}/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam(value = "expNumber") String expNumber) {
        ExpenditureResponse response = expenditureService.delete(expNumber);
        return Response.status(Response.Status.OK).entity(response).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
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

package com.geekydroid.savestmentbackend.resources.investment;


import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentFilterRequest;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentPortfolio;
import com.geekydroid.savestmentbackend.service.investment.InvestmentService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

import static com.geekydroid.savestmentbackend.utils.Constants.USER_ID_HEADER_PARAM_KEY;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/investment")
public class InvestmentResource {

    @Inject
    InvestmentService investmentService;

    @GET
    @Path("/{equityNumber}")
    public Response getById(
            @PathParam("equityNumber") String number
    ) {
        if (number == null || number.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        return Response.status(Response.Status.OK).entity(investmentService.getById(number)).build();
    }

    @POST()
    @Path("/bulk-create")
    @Transactional
    public Response createBulk(List<EquityItem> equityItems, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        return ResponseUtil.getResponseFromResult(investmentService.addEquityItems(equityItems, userId));
    }

    @POST()
    @Path("/create")
    public Response create(
            EquityItem request,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        request.setCreatedBy(userId);
        return Response.status(Response.Status.CREATED).entity(investmentService.create(request)).build();
    }

    @PUT()
    @Path("/{equityNumber}/update")
    @Transactional
    public Response update(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId,
            @PathParam("equityNumber") String equityNumber,
            EquityItem request
    ) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        request.setCreatedBy(userId);
        request.setInvestmentNumber(equityNumber);
        return Response.status(Response.Status.OK).entity(investmentService.update(request)).build();
    }

    @DELETE()
    @Path("/{equityNumber}/delete")
    @Transactional
    public Response delete(
            @PathParam("equityNumber") String equityNumber
    ) {
        if (equityNumber == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        return Response.status(Response.Status.OK).entity(investmentService.delete(equityNumber)).build();
    }

    @GET()
    @Path("/overview")
    public Response getInvestmentOverview(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        return ResponseUtil.getResponseFromResult(investmentService.getInvestmentOverview(startDate, endDate, userId));
    }

    @POST()
    @Path("/filterBy")
    public Response getInvestmentsBasedOnResults(
            InvestmentFilterRequest request,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        return ResponseUtil.getResponseFromResult(investmentService.getInvestmentItemsBasedOnGivenFilters(request,userId));
    }

    @POST
    @Path("/exportData")
    public Response exportInvestmentData(
            InvestmentFilterRequest request,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Investment Filter request cannot be null").build();
        }

        File file = investmentService.exportDataToExcel(request,userId);
        if (file == null) {
            return Response.serverError().build();
        }
        Response.ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.header("Content-Disposition","attachment; filename="+file.getName());
        file.deleteOnExit();
        return responseBuilder.build();
    }

    @GET
    @Path("/portfolio")
    public InvestmentPortfolio getInvestmentPortfolio(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        return investmentService.getInvestmentPortfolio(userId);
    }

}

package com.geekydroid.savestmentbackend.resources.investment;


import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentFilterRequest;
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

    @POST()
    @Path("/addEquity")
    @Transactional
    public Response addEquity(List<EquityItem> equityItems, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        return ResponseUtil.getResponseFromResult(investmentService.addEquityItems(equityItems, userId));
    }

    @PUT()
    @Path("/updateEquity/{equityNumber}")
    @Transactional
    public Response updateEquity(@PathParam("equityNumber") String equityNumber, EquityItem equityItem) {
        return ResponseUtil.getResponseFromResult(investmentService.updateEquityItems(equityNumber, equityItem));
    }

    @DELETE()
    @Path("/deleteEquity/{equityNumber}")
    @Transactional
    public Response deleteEquity(@PathParam("equityNumber") String equityNumber) {
        return ResponseUtil.getResponseFromResult(investmentService.deleteEquityItem(equityNumber));
    }

    @GET()
    @Path("/overview")
    public Response getInvestmentOverview(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        return ResponseUtil.getResponseFromResult(investmentService.getInvestmentOverview(startDate, endDate, userId));
    }

    @POST()
    @Path("/filterBy")
    public Response getExpenditureBasedOnFilters(
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

}

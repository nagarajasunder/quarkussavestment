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
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/investment")
public class InvestmentResource {

    @Inject
    InvestmentService investmentService;

    @POST()
    @Path("/addEquity")
    @Transactional
    public Response addEquity(List<EquityItem> equityItems) {
        System.out.println("Resource " + equityItems);
        return ResponseUtil.getResponseFromResult(investmentService.addEquityItems(equityItems));
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
    public Response getInvestmentOverview(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        return ResponseUtil.getResponseFromResult(investmentService.getInvestmentOverview(startDate, endDate));
    }

    @POST()
    @Path("/filterBy")
    public Response getExpenditureBasedOnFilters(
            InvestmentFilterRequest request
    ) {
        return ResponseUtil.getResponseFromResult(investmentService.getExpenditureItemBasedOnGivenFilters(request));
    }

}

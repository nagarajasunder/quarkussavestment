package com.geekydroid.savestmentbackend.resources.investment;


import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.service.investment.InvestmentService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
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

    public Response addEquity(List<EquityItem> equityItems) {
        return ResponseUtil.getResponseFromResult(investmentService.addEquityItems(equityItems));
    }

}

package com.geekydroid.savestmentbackend.resources.home;

import com.geekydroid.savestmentbackend.service.home.HomeService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("homescreen")
public class HomeResource {

    @Inject
    HomeService homeService;

    @GET
    public Response getHomeScreenData(
            @QueryParam("start_date") String startDate,
            @QueryParam("end_date") String endDate
    ) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return ResponseUtil.getResponseFromResult(homeService.getHomeScreenData(startDate,endDate));

    }

}

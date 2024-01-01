package com.geekydroid.savestmentbackend.resources.investment;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.expenditure.UpdateCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.CreateInvestmentCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.service.investment.InvestmentTypeService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.geekydroid.savestmentbackend.utils.Constants.USER_ID_HEADER_PARAM_KEY;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/investmentcategories")
public class InvestmentCategoryResource {

    @Inject
    InvestmentTypeService investmentTypeService;

    @GET
    public Response getInvestmentCategories() {
        return Response.status(Response.Status.OK).entity(investmentTypeService.getAllInvestmentCategories()).build();
    }

    @POST()
    @Path("/create")
    public Response createNewInvestmentCategory(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId,
            CreateInvestmentCategoryRequest request
    ) {
        if (request == null) {
            return ResponseUtil.getResponseFromResult(new Error(
                    Response.Status.BAD_REQUEST,
                    new BadRequestException("Invalid Request"),
                    new GenericNetworkResponse(Response.Status.BAD_REQUEST.getStatusCode(),"FAILED","Invalid Request",null)
            ));
        }
        request.setCreatedBy(userId);
        CategoryRespnose response = investmentTypeService.create(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT()
    @Path("/{investmentTypeId}/update")
    public Response update(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId,
            @PathParam("investmentTypeId") Long investmentTypeId,
            UpdateCategoryRequest request
    ) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        request.setUpdatedBy(userId);
        request.setCategoryId(investmentTypeId);
        CategoryRespnose response = investmentTypeService.update(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @DELETE()
    @Path("/{investmentTypeId}/delete")
    public Response delete(
            @PathParam("investmentTypeId") Long investmentTypeId
    ) {
        if (investmentTypeId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
        }
        CategoryRespnose response = investmentTypeService.delete(investmentTypeId);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

}

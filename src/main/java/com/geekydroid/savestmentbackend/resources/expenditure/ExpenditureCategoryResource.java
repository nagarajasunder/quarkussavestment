package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.expenditure.CreateExpenditureCategoryRequest;
import com.geekydroid.savestmentbackend.domain.expenditure.UpdateCategoryRequest;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureCategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.geekydroid.savestmentbackend.utils.Constants.USER_ID_HEADER_PARAM_KEY;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/expenditurecategories")
public class ExpenditureCategoryResource {

    @Inject
    ExpenditureCategoryService service;

    @POST()
    @Path("/create")
    public Response create(
            CreateExpenditureCategoryRequest request,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Request cannot be empty").build();
        }
        request.setCreatedBy(userId);
        CategoryRespnose respnose = service.create(request);
        return Response.status(Response.Status.CREATED).entity(respnose).build();
    }


    @PUT
    @Path("/{expenditureCategoryId}/update")
    public Response update(
            UpdateCategoryRequest request,
            @PathParam("expenditureCategoryId") Long expenditureCategoryId,
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId
    ) {
        if (request == null || expenditureCategoryId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Request cannot be empty").build();
        }
        request.setUpdatedBy(userId);
        request.setCategoryId(expenditureCategoryId);
        CategoryRespnose respnose = service.update(request);
        return Response.status(Response.Status.OK).entity(respnose).build();
    }

    @GET()
    @Path("/{expenditureTypeId}")
    public List<CategoryRespnose> getExpenditureCategories(
            @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId,
            @PathParam("expenditureTypeId") Long expenditureTypeId
    ) {
        return service.getExpenditureCategories(expenditureTypeId,userId);
    }

    @DELETE()
    @Path("/{expenditureCategoryId}/delete")
    public Response deleteExpenditureCategories(
            @PathParam("expenditureCategoryId") Long expenditureCategoryId
    ) {
        if (expenditureCategoryId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Categories to delete cannot be empty")
                    .build();
        }
        CategoryRespnose response = service.delete(expenditureCategoryId);
        return Response.status(Response.Status.OK).entity(response).build();
    }
}

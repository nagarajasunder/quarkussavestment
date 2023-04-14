package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureCategoryService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/expenditurecategory")
public class ExpenditureCategoryResource {

    @Inject
    ExpenditureCategoryService service;

    @POST()
    @Path("/{expenditure_type}/{category_name}")
    public Response createNewExpenditureCategory(
            @PathParam("expenditure_type") String expenditureType,
            @PathParam("category_name") String categoryName
    ) {
        System.out.println("Method Called");
        if (expenditureType == null || categoryName == null || expenditureType.isEmpty() || categoryName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Expenditure type or Category Name cannot be empty").build();
        }

        return ResponseUtil.getResponseFromResult(service.createNewExpenditureCategory(expenditureType,categoryName));
    }

    @GET()
    public List<ExpenditureCategoryResponse> getExpenditureCategories() {
        return service.getExpenditureCategoryResponse();
    }

    @POST()
    @Path("/delete")
    public Response deleteExpenditureCategories(List<String> expenditureCategories) {
        if (expenditureCategories == null || expenditureCategories.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Categories to delete cannot be empty")
                    .build();
        }
        return ResponseUtil.getResponseFromResult(service.deleteExpenditureCategories(expenditureCategories));
    }

    @POST()
    @Path("/edit/{existingCategory}")
    public Response editExpenditureCategory(
            @PathParam("existingCategory") String existingCategory,
            @QueryParam("new_value") String newValue
    ) {
        if (existingCategory == null || newValue == null || existingCategory.isEmpty() || newValue.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Expenditure category and updated category value should not be empty!")
                    .build();
        }
        else {
            return ResponseUtil.getResponseFromResult(service.updateExpenditureCategory(existingCategory,newValue));
        }
    }
}

package com.geekydroid.savestmentbackend.resources.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.service.expenditure.ExpenditureCategoryService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/expenditurecategory")
public class ExpenditureCategoryResource {

    @Inject
    ExpenditureCategoryService service;

    @GET
    public List<ExpenditureCategory> getAllCategories() {
        return service.getAllExpenditureCategories();
    }
}

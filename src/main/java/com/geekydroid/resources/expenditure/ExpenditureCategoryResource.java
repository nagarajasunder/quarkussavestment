package com.geekydroid.resources.expenditure;

import com.geekydroid.domain.expenditure.ExpenditureCategory;
import com.geekydroid.service.expenditure.ExpenditureCategoryService;
import com.geekydroid.service.expenditure.ExpenditureService;

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

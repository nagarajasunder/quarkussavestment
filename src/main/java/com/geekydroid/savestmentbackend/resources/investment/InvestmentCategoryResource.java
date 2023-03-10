package com.geekydroid.savestmentbackend.resources.investment;

import com.geekydroid.savestmentbackend.service.investment.InvestmentTypeServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/investmentcategories")
public class InvestmentCategoryResource {

    @Inject
    InvestmentTypeServiceImpl investmentTypeService;

    @GET
    public List<String> getInvestmentCategories() {
        return investmentTypeService.getAllInvestmentCategories();
    }

}

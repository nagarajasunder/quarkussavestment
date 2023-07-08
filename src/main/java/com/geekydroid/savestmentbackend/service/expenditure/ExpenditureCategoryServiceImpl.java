package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureCategoryRepository;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class ExpenditureCategoryServiceImpl implements ExpenditureCategoryService {

    @Inject
    ExpenditureCategoryRepository repository;

    @Inject
    ExpenditureService expenditureService;


    @Override
    public NetworkResponse createNewExpenditureCategory(String expenditureTypeName, String categoryName,String userId) {

        LocalDateTime now = LocalDateTime.now();
        String expenditureTypeQueryString = String.valueOf(expenditureTypeName.charAt(0)).toUpperCase() + expenditureTypeName.substring(1).toLowerCase();
        ExpenditureType expenditureType = ExpenditureType.find("expenditureName", expenditureTypeQueryString).firstResult();
        if (expenditureType == null) {
            return new Exception(Response.Status.BAD_REQUEST, new BadRequestException("Invalid Expenditure Type " + expenditureTypeName), null);
        }
        ExpenditureCategory newExpenditureCategory = new ExpenditureCategory(
                expenditureType,
                categoryName,
                false,
                userId,
                now,
                now
        );

        repository.createNewExpenditureCategory(newExpenditureCategory);

        return new Success(Response.Status.CREATED, null, new GenericNetworkResponse(Response.Status.CREATED.getStatusCode(),
                "success",
                "Expenditure category created successfully",
                null
        ));
    }

    @Override
    public List<ExpenditureCategory> getAllExpenditureCategories(String userId) {
        return repository.getAllExpenditureCategories(userId);
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr) {
        return repository.getExpenditureCategoryName(expenditureCategoryStr);
    }

    @Override
    public List<ExpenditureCategoryResponse> getExpenditureCategoryResponse(String userId) {
        return repository.getExpenditureCategoryResponse(userId);
    }

    @Override
    public NetworkResponse deleteExpenditureCategories(List<String> categoryNames) {
        try {
            expenditureService.deleteExpenditureByCategoryName(categoryNames);
            repository.deleteExpenditureCategoryByName(categoryNames);
            return new Success(Response.Status.OK, null,
                    new GenericNetworkResponse(
                            Response.Status.OK.getStatusCode(),
                            "Success",
                            "Expenditure Category deleted successfully",
                            null)
            );
        } catch (java.lang.Exception exception) {
            return new Exception(Response.Status.INTERNAL_SERVER_ERROR, exception, null);
        }
    }

    @Override
    public NetworkResponse updateExpenditureCategory(String existingCategory, String newValue) {
        try {
            LocalDateTime now = LocalDateTime.now();
            ExpenditureCategory.update("categoryName = ?1, updatedOn = ?2 where categoryName = ?3", newValue, now ,existingCategory);
            return new Success(
                    Response.Status.OK,
                    null,
                    new GenericNetworkResponse(
                            Response.Status.OK.getStatusCode(),
                            "success",
                            "Expenditure category updated successfully",
                            null
                    ));
        } catch (java.lang.Exception e) {
            return new Exception(Response.Status.INTERNAL_SERVER_ERROR, e, null);
        }
    }
}

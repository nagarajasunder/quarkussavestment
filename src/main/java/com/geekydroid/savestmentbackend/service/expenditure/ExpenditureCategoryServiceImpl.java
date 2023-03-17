package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureCategoryRepository;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class ExpenditureCategoryServiceImpl implements ExpenditureCategoryService {

    @Inject
    ExpenditureCategoryRepository repository;

    @Override
    public NetworkResponse createNewExpenditureCategory(String expenditureTypeName, String categoryName) {

        LocalDateTime now = LocalDateTime.now();
        ExpenditureType expenditureType = ExpenditureType.find("expenditureName", expenditureTypeName).firstResult();
        if (expenditureType == null) {
            return new Error(Response.Status.BAD_REQUEST, new BadRequestException("Invalid Expenditure Type " + expenditureTypeName), null);
        }
        ExpenditureCategory newExpenditureCategory = new ExpenditureCategory(
                expenditureType,
                categoryName,
                "",
                now,
                now
        );

        repository.createNewExpenditureCategory(newExpenditureCategory);

        return new Success(Response.Status.CREATED, null, null);
    }

    @Override
    public List<ExpenditureCategory> getAllExpenditureCategories() {
        return repository.getAllExpenditureCategories();
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr) {
        return repository.getExpenditureCategoryName(expenditureCategoryStr);
    }

    @Override
    public List<ExpenditureCategoryResponse> getExpenditureCategoryResponse() {
        return repository.getExpenditureCategoryResponse();
    }
}

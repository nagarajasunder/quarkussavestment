package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public interface ExpenditureCategoryRepository {

    ExpenditureCategory create(ExpenditureCategory expenditureCategory);

    ExpenditureCategory update(ExpenditureCategory expenditureCategory);

    ExpenditureCategory delete(ExpenditureCategory expenditureCategory);

    ExpenditureCategory getById(Long id);

    ExpenditureCategory getByName(Long expenditureTypeId, String name,String createdBy);

    List<ExpenditureCategory> getAllExpenditureCategories(String userId);

    ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr);

    List<CategoryRespnose> getExpenditureCategoryResponse(Long expenditureTypeId, String userId);

}

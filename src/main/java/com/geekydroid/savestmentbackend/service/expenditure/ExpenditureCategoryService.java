package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.CreateExpenditureCategoryRequest;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.expenditure.UpdateCategoryRequest;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryService {

    CategoryRespnose create(CreateExpenditureCategoryRequest request);

    ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr);

    List<CategoryRespnose> getExpenditureCategories(Long expenditureTypeId, String userId);

    CategoryRespnose update(UpdateCategoryRequest request);

    CategoryRespnose delete(Long id);
}

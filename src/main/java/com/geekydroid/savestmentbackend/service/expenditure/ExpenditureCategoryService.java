package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryService {

    NetworkResponse createNewExpenditureCategory(String expenditureType, String categoryName);

    List<ExpenditureCategory> getAllExpenditureCategories();

    ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr);

    List<ExpenditureCategoryResponse> getExpenditureCategoryResponse();

    NetworkResponse deleteExpenditureCategories(List<String> categoryNames);

    NetworkResponse updateExpenditureCategory(String existingCategory, String newValue);
}

package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryService {

    List<ExpenditureCategory> getAllExpenditureCategories();

    ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr);

    List<ExpenditureCategoryResponse> getExpenditureCategoryResponse();
}

package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryRepository {

    void createNewExpenditureCategory(ExpenditureCategory expenditureCategory);

    List<ExpenditureCategory> getAllExpenditureCategories();

    ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr);

    List<ExpenditureCategoryResponse> getExpenditureCategoryResponse();

}

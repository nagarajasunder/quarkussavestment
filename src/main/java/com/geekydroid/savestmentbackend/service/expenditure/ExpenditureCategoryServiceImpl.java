package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ExpenditureCategoryServiceImpl implements ExpenditureCategoryService{

    @Inject
    ExpenditureCategoryRepository repository;

    @Override
    public List<ExpenditureCategory> getAllExpenditureCategories() {
        return repository.getAllExpenditureCategories();
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr) {
        return repository.getExpenditureCategoryName(expenditureCategoryStr);
    }
}

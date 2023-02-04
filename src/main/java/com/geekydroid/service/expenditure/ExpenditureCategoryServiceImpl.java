package com.geekydroid.service.expenditure;

import com.geekydroid.domain.expenditure.ExpenditureCategory;
import com.geekydroid.repository.expenditure.ExpenditureCategoryRepository;

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
}

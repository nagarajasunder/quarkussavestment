package com.geekydroid.service.expenditure;

import com.geekydroid.domain.expenditure.ExpenditureCategory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryService {

    List<ExpenditureCategory> getAllExpenditureCategories();

    ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr);
}

package com.geekydroid.repository.expenditure;

import com.geekydroid.domain.expenditure.ExpenditureCategory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureCategoryRepository {

    public void createNewExpenditureCategory(ExpenditureCategory expenditureCategory);

    public List<ExpenditureCategory> getAllExpenditureCategories();
}

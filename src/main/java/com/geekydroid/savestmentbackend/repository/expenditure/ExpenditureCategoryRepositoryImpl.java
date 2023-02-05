package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.jooq.impl.DSL.table;

@ApplicationScoped
@Transactional

public class ExpenditureCategoryRepositoryImpl implements ExpenditureCategoryRepository {


    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext context;

    @Override
    public void createNewExpenditureCategory(ExpenditureCategory expenditureCategory) {
        entityManager.persist(expenditureCategory);
    }

    @Override
    public List<ExpenditureCategory> getAllExpenditureCategories() {
        return context.select().from(table("EXPENDITURE_CATEGORY")).fetchInto(ExpenditureCategory.class);
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr) {
        return ExpenditureCategory.find("categoryName",expenditureCategoryStr).firstResult();
    }

}

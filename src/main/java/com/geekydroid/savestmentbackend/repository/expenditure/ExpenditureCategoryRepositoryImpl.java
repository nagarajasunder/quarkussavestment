package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.EXPENDITURE_CATEGORY;

@ApplicationScoped
@Transactional

public class ExpenditureCategoryRepositoryImpl implements ExpenditureCategoryRepository {


    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext context;

    @Override
    public ExpenditureCategory create(ExpenditureCategory expenditureCategory) {
        entityManager.persist(expenditureCategory);
        return expenditureCategory;
    }


    @Override
    public ExpenditureCategory update(ExpenditureCategory expenditureCategory) {
        entityManager.merge(expenditureCategory);
        return expenditureCategory;
    }

    @Override
    public ExpenditureCategory delete(ExpenditureCategory expenditureCategory) {
        entityManager.remove(expenditureCategory);
        return expenditureCategory;
    }

    @Override
    public ExpenditureCategory getById(Long id) {
        return entityManager.find(ExpenditureCategory.class, id);
    }

    @Override
    public ExpenditureCategory getByName(Long expenditureTypeId, String name, String createdBy) {
        return entityManager
                .createQuery("select e from ExpenditureCategory e where e.expenditureType.id=?1 and lower(e.categoryName)=?2 and e.createdBy=?3", ExpenditureCategory.class)
                .setParameter(1, expenditureTypeId)
                .setParameter(2, name)
                .setParameter(3, createdBy)
                .getResultList().stream().findFirst().orElse(null);

    }


    @Override
    public List<ExpenditureCategory> getAllExpenditureCategories(String userId) {
        return context.select()
                .from(EXPENDITURE_CATEGORY)
                .where(EXPENDITURE_CATEGORY.IS_COMMON.eq(true).or(EXPENDITURE_CATEGORY.CREATED_BY.eq(userId)))
                .fetchInto(ExpenditureCategory.class);
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr) {
       return null;
       //Todo()
    }

    @Override
    public List<CategoryRespnose> getExpenditureCategoryResponse(Long expenditureTypeId, String userId) {

        return context.select(
                        EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID,
                        EXPENDITURE_CATEGORY.CATEGORY_NAME
                )
                .from(EXPENDITURE_CATEGORY)
                .where(EXPENDITURE_CATEGORY.IS_COMMON.eq(true).or(EXPENDITURE_CATEGORY.CREATED_BY.eq(userId)))
                .and(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID.eq(expenditureTypeId))
                .fetchInto(CategoryRespnose.class);

    }


}

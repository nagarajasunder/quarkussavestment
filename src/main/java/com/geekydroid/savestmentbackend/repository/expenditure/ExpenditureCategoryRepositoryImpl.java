package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.EXPENDITURE_CATEGORY;
import static com.geekydroid.savestment.domain.db.Tables.EXPENDITURE_TYPE;
import static org.jooq.impl.DSL.field;
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
        return context.select()
                .from(EXPENDITURE_CATEGORY).fetchInto(ExpenditureCategory.class);
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr) {
        return ExpenditureCategory.find("categoryName", expenditureCategoryStr).firstResult();
    }

    @Override
    public List<ExpenditureCategoryResponse> getExpenditureCategoryResponse() {
        List<String> expenditureTypeNames = context
                .select(EXPENDITURE_TYPE.EXPENDITURE_NAME)
                .from(EXPENDITURE_TYPE)
                .fetchInto(String.class);
        HashMap<String, ExpenditureCategoryResponse> expenditureCategoryMap = new HashMap<>();

        for (String expenditureTypeName : expenditureTypeNames) {
            expenditureCategoryMap.put(expenditureTypeName, new ExpenditureCategoryResponse(expenditureTypeName, new ArrayList<>()));
        }
        System.out.println("Map size "+expenditureCategoryMap.size());

        Result<Record2<String, String>> expenditureCategories = context.select(
                EXPENDITURE_TYPE.EXPENDITURE_NAME,
                        EXPENDITURE_CATEGORY.CATEGORY_NAME)
                .from(EXPENDITURE_TYPE)
                .join(EXPENDITURE_CATEGORY)
                .on(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID)).fetch();

        for (Record r : expenditureCategories) {
            String expenditureTypeName = r.getValue(EXPENDITURE_TYPE.EXPENDITURE_NAME);
            String expenditureCategoryName = r.getValue(EXPENDITURE_CATEGORY.CATEGORY_NAME);
            ExpenditureCategoryResponse response = expenditureCategoryMap.get(expenditureTypeName);
            response.addExpenditureCategory(expenditureCategoryName);
        }

        return expenditureCategoryMap.values().stream().toList();
    }

}

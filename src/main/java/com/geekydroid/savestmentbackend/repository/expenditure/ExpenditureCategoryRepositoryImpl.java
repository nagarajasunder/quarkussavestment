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
        return context.select().from(table("EXPENDITURE_CATEGORY")).fetchInto(ExpenditureCategory.class);
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryName(String expenditureCategoryStr) {
        return ExpenditureCategory.find("categoryName", expenditureCategoryStr).firstResult();
    }

    @Override
    public List<ExpenditureCategoryResponse> getExpenditureCategoryResponse() {
        List<String> expenditureTypeNames = context.select(field("expenditure_name")).from(table("EXPENDITURE_TYPE")).fetchInto(String.class);
        HashMap<String, ExpenditureCategoryResponse> expenditureCategoryMap = new HashMap<>();

        for (String expenditureTypeName : expenditureTypeNames) {
            expenditureCategoryMap.put(expenditureTypeName, new ExpenditureCategoryResponse(expenditureTypeName, new ArrayList<>()));
        }
        System.out.println("Map size "+expenditureCategoryMap.size());

        Result<Record2<Object, Object>> expenditureCategories = context.select(
                        field("EXPENDITURE_TYPE.expenditure_name"),
                        field("EXPENDITURE_CATEGORY.category_name")
                ).from(table("EXPENDITURE_TYPE"))
                .join(table("EXPENDITURE_CATEGORY"))
                .on(field("EXPENDITURE_TYPE.expenditure_type_id").eq(field("EXPENDITURE_CATEGORY.expenditure_type_expenditure_type_id"))).fetch();

        for (Record r : expenditureCategories) {
            String expenditureTypeName = r.getValue(field("EXPENDITURE_TYPE.expenditure_name")).toString();
            String expenditureCategoryName = r.getValue(field("EXPENDITURE_CATEGORY.category_name")).toString();
            ExpenditureCategoryResponse response = expenditureCategoryMap.get(expenditureTypeName);
            response.addExpenditureCategory(expenditureCategoryName);
        }

        return expenditureCategoryMap.values().stream().toList();
    }

}

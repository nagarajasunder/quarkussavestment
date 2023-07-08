package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategoryResponse;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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
        expenditureCategory.persist();
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
        return ExpenditureCategory.find("categoryName", expenditureCategoryStr).firstResult();
    }

    @Override
    public List<ExpenditureCategoryResponse> getExpenditureCategoryResponse(String userId) {

        List<ExpenditureCategoryResponse> response = new ArrayList<>();

        Result<Record2<String, String>> expenditureCategories = context.select(
                        EXPENDITURE_TYPE.EXPENDITURE_NAME,
                        field("string_agg(DISTINCT CATEGORY_NAME::text,',')", SQLDataType.VARCHAR).as(EXPENDITURE_CATEGORY.CATEGORY_NAME)
                )
                .from(EXPENDITURE_TYPE)
                .join(EXPENDITURE_CATEGORY)
                .on(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID))
                .where(EXPENDITURE_CATEGORY.IS_COMMON.eq(true).or(EXPENDITURE_CATEGORY.CREATED_BY.eq(userId)))
                .groupBy(EXPENDITURE_TYPE.EXPENDITURE_NAME)
                .fetch();

        for (Record r : expenditureCategories) {
            String expenditureTypeName = r.getValue(EXPENDITURE_TYPE.EXPENDITURE_NAME);
            List<String> expenditureCategoryList = Arrays.stream(r.getValue(EXPENDITURE_CATEGORY.CATEGORY_NAME).split(",")).toList();
            response.add(new ExpenditureCategoryResponse(expenditureTypeName, expenditureCategoryList));
        }

        return response;
    }

    @Override
    public void deleteExpenditureCategoryByName(List<String> categoryName) {
        ExpenditureCategory.deleteExpenditureCategoryByName(categoryName);
    }

}

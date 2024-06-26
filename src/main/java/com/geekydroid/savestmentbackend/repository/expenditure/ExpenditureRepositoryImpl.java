package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.CategoryWiseExpense;
import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureItem;
import com.geekydroid.savestmentbackend.utils.converters.PaymodeConverters;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.*;
import static org.jooq.impl.DSL.*;

@ApplicationScoped
@Transactional
public class ExpenditureRepositoryImpl implements ExpenditureRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext context;

    @Override
    public Expenditure create(Expenditure expenditure) {
        entityManager.persist(expenditure);
        return expenditure;

    }

    @Override
    @Transactional
    public Expenditure update(Expenditure expenditure) {
        return entityManager.merge(expenditure);

    }

    @Override
    public Expenditure delete(Expenditure expenditure) {
        entityManager.remove(expenditure);
        return expenditure;
    }

    @Override
    public Expenditure getExpenditureByExpNumber(String expNumber) {
        return entityManager.createQuery("SELECT e from Expenditure  e WHERE e.id=?1",Expenditure.class)
                .setParameter(1,expNumber)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Double> getTotalExpenseAndIncomeAmount(String userId,LocalDate startDate, LocalDate endDate) {

        Condition dateFilter = EXPENDITURE.DATE_OF_EXPENDITURE.between(startDate, endDate).and(EXPENDITURE.CREATED_BY.eq(userId));

        Result<Record2<BigDecimal, String>> result = context
                .select(
                        sum(
                                DSL.when(dateFilter, EXPENDITURE.EXPENDITURE_AMOUNT).otherwise(BigDecimal.ZERO)
                        ).as("totalAmount"),
                        EXPENDITURE_TYPE.EXPENDITURE_NAME.as("expenditureName")
                )
                .from(EXPENDITURE)
                .leftJoin(EXPENDITURE_CATEGORY)
                .on(EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID))
                .leftJoin(EXPENDITURE_TYPE).on(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID.eq(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID))
                .groupBy(
                        EXPENDITURE_TYPE.EXPENDITURE_NAME
                ).fetch();
        double totalExpense = 0.0;
        double totalIncome = 0.0;

        /*
         * Currently there are only two expenditure types
         * 1. Expense
         * 2. Income
         * So the size should be at least 2
         */
        for (Record r : result) {
            if (r.getValue("expenditureName") != null && r.getValue("totalAmount") != null) {
                String expenditureTypeName = r.getValue("expenditureName").toString();
                BigDecimal expenditureAmount = (BigDecimal) r.getValue("totalAmount");
                if (expenditureTypeName.equalsIgnoreCase("income")) {
                    totalIncome = expenditureAmount.doubleValue();
                } else if (expenditureTypeName.equalsIgnoreCase("expense")) {
                    totalExpense = expenditureAmount.doubleValue();
                }
            }
        }
        List<Double> expenditureList = new ArrayList<>();
        expenditureList.add(0, totalExpense);
        expenditureList.add(1, totalIncome);
        return expenditureList;
    }


    @Override
    public List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            List<Paymode> paymode,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<Long> expenditureCategories,
            int pageNo,
            int itemsPerPage
    ) {
        Condition condition = chainFilters(expenditureType, paymode, fromDate, toDate, userId,expenditureCategories);
        SelectConditionStep<Record8<String, LocalDate, String, String, Long, String, BigDecimal, Paymode>> selectQuery = context.select(
                        EXPENDITURE.EXPENDITURE_NUMBER.as("expenditureNumber"),
                        EXPENDITURE.DATE_OF_EXPENDITURE.as("expenditureDate"),
                        EXPENDITURE.EXPENDITURE_DESCRIPTION.as("expenditureDescription"),
                        EXPENDITURE_CATEGORY.CATEGORY_NAME.as("expenditureCategory"),
                        EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID.as("expenditureCategoryId"),
                        EXPENDITURE_TYPE.EXPENDITURE_NAME.as("expenditureType"),
                        EXPENDITURE.EXPENDITURE_AMOUNT.as("expenditureAmount"),
                        EXPENDITURE.MODE_OF_PAYMENT.convert(PaymodeConverters.getConverter()).as("paymode"))
                .from(EXPENDITURE)
                .leftJoin(EXPENDITURE_CATEGORY)
                .on(EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID))
                .leftJoin(EXPENDITURE_TYPE)
                .on(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID.eq(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID))
                .where(
                        condition
                );

        if (pageNo > 0 && itemsPerPage > 0) {
            int offset = ((pageNo - 1) * itemsPerPage);
            return selectQuery
                    .orderBy(EXPENDITURE.DATE_OF_EXPENDITURE.desc(),EXPENDITURE.EXPENDITURE_NUMBER.asc())
                    .limit(itemsPerPage)
                    .offset(offset)
                    .fetchInto(ExpenditureItem.class);
        } else {
            return selectQuery
                    .orderBy(EXPENDITURE.DATE_OF_EXPENDITURE.desc(),EXPENDITURE.EXPENDITURE_NUMBER.asc())
                    .fetchInto(ExpenditureItem.class);
        }
    }

    @Override
    public List<String> getExpenditureNumberFromCategoryName(List<String> categoryName) {
        return context.select(EXPENDITURE.EXPENDITURE_NUMBER)
                .from(EXPENDITURE)
                .leftJoin(EXPENDITURE_CATEGORY)
                .on(EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID))
                .where(EXPENDITURE_CATEGORY.CATEGORY_NAME.in(categoryName))
                .fetchInto(String.class);
    }

    private Condition chainFilters(String expenditureType, List<Paymode> paymode, LocalDate fromDate, LocalDate toDate, String userId,List<Long> expenditureCategories) {
        Condition condition = noCondition();

        if (userId != null && !userId.isEmpty()) {
            condition = condition.and(EXPENDITURE.CREATED_BY.eq(userId));
        }

        if (expenditureType != null && !expenditureType.isEmpty()) {
            condition = condition.and(EXPENDITURE_TYPE.EXPENDITURE_NAME.equalIgnoreCase(expenditureType));
        }
        if (fromDate != null) {
            condition = condition.and(EXPENDITURE.DATE_OF_EXPENDITURE.greaterOrEqual(fromDate));
        }
        if (toDate != null) {

            condition = condition.and(EXPENDITURE.DATE_OF_EXPENDITURE.lessOrEqual(toDate));
        }
        if (paymode != null && !paymode.isEmpty()) {
            condition = condition.and(EXPENDITURE.MODE_OF_PAYMENT.convert(PaymodeConverters.getConverter()).in(paymode));
        }

        if (expenditureCategories != null && !expenditureCategories.isEmpty()) {
            condition = condition.and(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID.in(expenditureCategories));
        }
        return condition;
    }

    @Override
    public List<CategoryWiseExpense> getCategoryWiseExpenseByGivenDateRange(String userId,LocalDate startDate, LocalDate endDate) {

        Field<BigDecimal> totalExpenditure = DSL.sum(EXPENDITURE.EXPENDITURE_AMOUNT).as("total_expenditure");


        return context.select(
                EXPENDITURE_CATEGORY.CATEGORY_NAME,
                totalExpenditure
        ).from(
                EXPENDITURE
        ).leftJoin(
                EXPENDITURE_CATEGORY
        ).on(
                EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID)
        ).join(
                EXPENDITURE_TYPE
        ).on(
                EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID.eq(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID).and(EXPENDITURE_TYPE.EXPENDITURE_NAME.eq("Expense"))
        ).where(
                EXPENDITURE.CREATED_BY.eq(userId).and(EXPENDITURE.DATE_OF_EXPENDITURE.between(startDate, endDate))
        ).groupBy(
                EXPENDITURE_CATEGORY.CATEGORY_NAME
        ).orderBy(totalExpenditure.desc()).fetchInto(CategoryWiseExpense.class);
    }

    @Override
    public void deleteExpendituresByCategoryId(Long categoryId) {
        entityManager.createQuery(
                "delete from Expenditure e where e.expenditureCategory.id=?1"
        ).setParameter(1,categoryId);
    }
}

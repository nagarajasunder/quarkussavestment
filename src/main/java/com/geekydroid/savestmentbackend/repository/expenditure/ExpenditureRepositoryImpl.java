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
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.sum;

@ApplicationScoped
@Transactional
public class ExpenditureRepositoryImpl implements ExpenditureRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext context;

    @Override
    public Expenditure createExpenditure(Expenditure expenditure) {
        entityManager.persist(expenditure);
        return expenditure;

    }

    @Override
    public Expenditure updateExpenditure(String expNumber, Expenditure expenditure) {
        return entityManager.merge(expenditure);

    }

    @Override
    public Expenditure deleteExpenditure(String expNumber) {
        Expenditure expenditure = entityManager.find(Expenditure.class, expNumber);
        if (expenditure == null) {
            return null;
        } else {
            entityManager.remove(expenditure);
            return expenditure;
        }

    }

    @Override
    public Expenditure getExpenditureByExpNumber(String expNumber) {
        return entityManager.find(Expenditure.class, expNumber);
    }

    @Override
    public List<Double> getTotalExpenseAndIncomeAmount(LocalDate startDate, LocalDate endDate) {

        Condition dateFilter = EXPENDITURE.DATE_OF_EXPENDITURE.between(startDate, endDate);

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
        if ((long) result.size() >= 2L) {
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
        }
        List<Double> expenditureList = new ArrayList<>();
        expenditureList.add(0, totalExpense);
        expenditureList.add(1, totalIncome);
        System.out.println(expenditureList);
        return expenditureList;
    }

    @Override
    public List<ExpenditureItem> getExpenditureByGivenDateRange(LocalDate startDate, LocalDate endDate) {


        List<ExpenditureItem> result = context.select(
                        EXPENDITURE.EXPENDITURE_NUMBER.as("expenditureNumber"),
                        EXPENDITURE.DATE_OF_EXPENDITURE.as("expenditureDate"),
                        EXPENDITURE.EXPENDITURE_DESCRIPTION.as("expenditureDescription"),
                        EXPENDITURE_CATEGORY.CATEGORY_NAME.as("expenditureCategory"),
                        EXPENDITURE_TYPE.EXPENDITURE_NAME.as("expenditureType"),
                        EXPENDITURE.EXPENDITURE_AMOUNT.as("expenditureAmount"),
                        EXPENDITURE.MODE_OF_PAYMENT.convert(PaymodeConverters.getConverter()).as("paymode")
                ).from(EXPENDITURE)
                .leftJoin(EXPENDITURE_CATEGORY)
                .on(
                        EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID.eq(EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID)
                ).leftJoin(
                        EXPENDITURE_TYPE
                ).on(EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID.eq(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID))
                .where(EXPENDITURE.DATE_OF_EXPENDITURE.between(startDate, endDate))
                .fetchInto(ExpenditureItem.class);
        return result;
    }

    @Override
    public List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            List<Paymode> paymode,
            LocalDate fromDate,
            LocalDate toDate,
            List<String> expenditureCategories
    ) {

        Condition condition = noCondition();

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

        if (expenditureCategories != null && expenditureCategories.size() > 0) {
            condition = condition.and(EXPENDITURE_CATEGORY.CATEGORY_NAME.in(expenditureCategories));
        }

        return context.select(
                        EXPENDITURE.EXPENDITURE_NUMBER.as("expenditureNumber"),
                        EXPENDITURE.DATE_OF_EXPENDITURE.as("expenditureDate"),
                        EXPENDITURE.EXPENDITURE_DESCRIPTION.as("expenditureDescription"),
                        EXPENDITURE_CATEGORY.CATEGORY_NAME.as("expenditureCategory"),
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
                )
                .fetchInto(ExpenditureItem.class);
    }

    @Override
    public List<CategoryWiseExpense> getCategoryWiseExpenseByGivenDateRange(LocalDate startDate, LocalDate endDate) {


        return context.select(
                EXPENDITURE_CATEGORY.CATEGORY_NAME,
                sum(EXPENDITURE.EXPENDITURE_AMOUNT)
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
                EXPENDITURE.DATE_OF_EXPENDITURE.between(startDate, endDate)
        ).groupBy(
                EXPENDITURE_CATEGORY.CATEGORY_NAME
        ).fetchInto(CategoryWiseExpense.class);
    }
}

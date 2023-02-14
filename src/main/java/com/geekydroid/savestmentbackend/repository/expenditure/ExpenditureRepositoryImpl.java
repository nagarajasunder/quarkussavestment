package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureItem;
import com.geekydroid.savestmentbackend.utils.converters.PaymodeConverters;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public List<Double> getTotalExpenseAndIncomeAmount(String startDate, String endDate) {
        Result<Record2<BigDecimal, String>> result = context.select(
                        DSL.sum(DSL.field("EXPENDITURE.EXPENDITURE_AMOUNT", SQLDataType.DOUBLE)).as("totalAmount"),
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_NAME", SQLDataType.VARCHAR).as("expenditureName")
                ).from(DSL.table("EXPENDITURE"))
                .leftJoin(DSL.table("EXPENDITURE_CATEGORY"))
                .on(
                        DSL.field("EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID")
                                .eq(DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID"))
                ).leftJoin(
                        DSL.table("EXPENDITURE_TYPE")
                ).on(
                        DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID")
                                .eq(DSL.field("EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID"))
                )
                .where(DSL.field("TO_CHAR(EXPENDITURE.DATE_OF_EXPENDITURE,'YYYY/MM/DD')").between(startDate, endDate))
                .groupBy(
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID")
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
        expenditureList.add(totalExpense);
        expenditureList.add(totalIncome);
        return expenditureList;
    }

    @Override
    public List<ExpenditureItem> getExpenditureByGivenDateRange(String startDate, String endDate) {


        List<ExpenditureItem> result = context.select(
                        (DSL.field("EXPENDITURE.EXPENDITURE_NUMBER", SQLDataType.VARCHAR)).as("expenditureNumber"),
                        DSL.field("EXPENDITURE.DATE_OF_EXPENDITURE", SQLDataType.DATE).as("expenditureDate"),
                        DSL.field("EXPENDITURE.EXPENDITURE_DESCRIPTION", SQLDataType.VARCHAR).as("expenditureDescription"),
                        DSL.field("EXPENDITURE_CATEGORY.CATEGORY_NAME", SQLDataType.VARCHAR).as("expenditureCategory"),
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_NAME", SQLDataType.VARCHAR).as("expenditureType"),
                        DSL.field("EXPENDITURE.EXPENDITURE_AMOUNT", SQLDataType.DOUBLE).as("expenditureAmount"),
                        DSL.field("EXPENDITURE.MODE_OF_PAYMENT", SQLDataType.INTEGER.asConvertedDataType(PaymodeConverters.getConverter())).as("paymode")
                ).from(DSL.table("EXPENDITURE"))
                .leftJoin(DSL.table("EXPENDITURE_CATEGORY"))
                .on(
                        DSL.field("EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID")
                                .eq(DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID"))
                ).leftJoin(
                        DSL.table("EXPENDITURE_TYPE")
                ).on(
                        DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID")
                                .eq(DSL.field("EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID"))
                )
                .where(DSL.field("TO_CHAR(EXPENDITURE.DATE_OF_EXPENDITURE,'YYYY/MM/DD')").between(startDate, endDate))
                .fetchInto(ExpenditureItem.class);
        System.out.println(result);
        return result;
    }

    @Override
    public List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            Paymode paymode,
            String fromDate,
            String toDate,
            List<String> expenditureCategories
    ) {
        Condition expenditureTypeFilter = DSL.condition("1=1");
        if (!expenditureType.isEmpty()) {
            expenditureTypeFilter = DSL.field("EXPENDITURE_TYPE.EXPENDITURE_NAME").eq(expenditureTypeFilter);
        }

        Condition fromDateFilter = DSL.condition("1=1");

        if (!fromDate.isEmpty()) {
            fromDateFilter = DSL.field("EXPENDITURE.DATE_OF_EXPENDITURE").greaterOrEqual(fromDate);
        }

        Condition toDateFilter = DSL.condition("1=1");

        if (!toDate.isEmpty()) {
            toDateFilter = DSL.field("EXPENDITURE.DATE_OF_EXPENDITURE").lessOrEqual(toDate);
        }

        Condition paymodeFilter = DSL.condition("1=1");
        if (paymode != null) {
            paymodeFilter = DSL.field("EXPENDITURE.MODE_OF_PAYMENT",SQLDataType.INTEGER.asConvertedDataType(PaymodeConverters.getConverter())).eq(paymode);
        }

        Condition expenditureCategoryFilter = DSL.condition("1=1");
        if (expenditureCategories.size() > 0) {
            expenditureCategoryFilter = DSL.field("EXPENDITURE_CATEGORY.CATEGORY_NAME").in(expenditureCategories);
        }


        List<ExpenditureItem> result = context.select(
                        (DSL.field("EXPENDITURE.EXPENDITURE_NUMBER", SQLDataType.VARCHAR)).as("expenditureNumber"),
                        DSL.field("EXPENDITURE.DATE_OF_EXPENDITURE", SQLDataType.DATE).as("expenditureDate"),
                        DSL.field("EXPENDITURE.EXPENDITURE_DESCRIPTION", SQLDataType.VARCHAR).as("expenditureDescription"),
                        DSL.field("EXPENDITURE_CATEGORY.CATEGORY_NAME", SQLDataType.VARCHAR).as("expenditureCategory"),
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_NAME", SQLDataType.VARCHAR).as("expenditureType"),
                        DSL.field("EXPENDITURE.EXPENDITURE_AMOUNT", SQLDataType.DOUBLE).as("expenditureAmount"),
                        DSL.field("EXPENDITURE.MODE_OF_PAYMENT", SQLDataType.INTEGER.asConvertedDataType(PaymodeConverters.getConverter())).as("paymode")
                ).from(DSL.table("EXPENDITURE"))
                .leftJoin(DSL.table("EXPENDITURE_CATEGORY"))
                .on(
                        DSL.field("EXPENDITURE.EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID")
                                .eq(DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_CATEGORY_ID"))
                ).leftJoin(
                        DSL.table("EXPENDITURE_TYPE")
                ).on(
                        DSL.field("EXPENDITURE_CATEGORY.EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID")
                                .eq(DSL.field("EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID"))
                )
                .where(DSL.field("TO_CHAR(EXPENDITURE.DATE_OF_EXPENDITURE,'YYYY/MM/DD')").between(fromDate, toDate))
                .and(expenditureTypeFilter)
                .and(paymodeFilter)
                .and(fromDateFilter)
                .and(toDateFilter)
                .and(expenditureCategoryFilter)
                .fetchInto(ExpenditureItem.class);
        System.out.println(result);
        return result;
    }
}

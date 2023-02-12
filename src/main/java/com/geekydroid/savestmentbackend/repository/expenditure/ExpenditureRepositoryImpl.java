package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_NAME",SQLDataType.VARCHAR).as("expenditureName")
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
                ).groupBy(
                        DSL.field("EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID")
                ).fetch();
        System.out.println("Result "+result.size());
        System.out.println(result);

        Double totalExpense = 0.0;
        Double totalIncome = 0.0;

        if ((long) result.size() >= 2L) {
            for (Record r : result) {
                String expenditureTypeName = r.getValue("expenditureName").toString();
                BigDecimal expenditureAmount = (BigDecimal) r.getValue("totalAmount");
                if (expenditureTypeName.equalsIgnoreCase("income")) {
                    totalIncome = expenditureAmount.doubleValue();
                }
                else if (expenditureTypeName.equalsIgnoreCase("expense")){
                    totalExpense = expenditureAmount.doubleValue();
                }
            }
        }
        System.out.println("Total Expense "+totalExpense);
        System.out.println("Total Income "+totalIncome);
        List<Double> expenditureList = new ArrayList<>();
        expenditureList.add(totalExpense);
        expenditureList.add(totalIncome);
        return expenditureList;
    }
}

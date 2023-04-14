package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.CategoryWiseExpense;
import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureItem;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@ApplicationScoped
public interface ExpenditureRepository {

    Expenditure createExpenditure(Expenditure expenditure);

    Expenditure updateExpenditure(String expNumber, Expenditure expenditure);

    Expenditure deleteExpenditure(String expNumber);

    Expenditure getExpenditureByExpNumber(String expNumber);

    List<Double> getTotalExpenseAndIncomeAmount(LocalDate startDate, LocalDate endDate);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            List<Paymode> paymodes,
            LocalDate fromDate,
            LocalDate toDate,
            List<String> expenditureCategories,
            int limit
    );

    List<String> getExpenditureNumberFromCategoryName(List<String> categoryName);

    List<CategoryWiseExpense> getCategoryWiseExpenseByGivenDateRange(LocalDate startDate,LocalDate endDate);
}

package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
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

    List<Double> getTotalExpenseAndIncomeAmount(String startDate, String endDate);

    List<ExpenditureItem> getExpenditureByGivenDateRange(String startDate, String endDate);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            Paymode paymode,
            String fromDate,
            String toDate,
            List<String> expenditureCategories
    );
}

package com.geekydroid.savestmentbackend.domain.expenditure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class ExpenditureOverview {


    private Double totalExpenditure;

    private Double totalExpense;

    private Double totalIncome;

    private List<CategoryWiseExpense> categoryWiseExpenses;

    private List<ExpenditureItem> recentExpenditures;

    @Override
    public String toString() {
        return "ExpenditureOverview{" +
                "totalExpenditure=" + totalExpenditure +
                ", totalExpense=" + totalExpense +
                ", totalIncome=" + totalIncome +
                ", recentExpenditures=" + recentExpenditures +
                '}';
    }
}

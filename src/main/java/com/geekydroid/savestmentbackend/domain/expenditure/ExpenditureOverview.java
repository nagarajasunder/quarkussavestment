package com.geekydroid.savestmentbackend.domain.expenditure;

import jdk.jfr.Category;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class ExpenditureOverview {

    @JsonbProperty("total_expenditure")
    private Double totalExpenditure;

    @JsonbProperty("total_expense")
    private Double totalExpense;

    @JsonbProperty("total_income")
    private Double totalIncome;

    @JsonbProperty("category_wise_expense")
    private List<CategoryWiseExpense> categoryWiseExpenses;

    @JsonbProperty("recent_expenditures")
    private List<ExpenditureItem> recentExpenditures;

    public ExpenditureOverview(Double totalExpenditure,
                               Double totalExpense,
                               Double totalIncome,
                               List<CategoryWiseExpense> categoryWiseExpenses,
                               List<ExpenditureItem> recentExpenditures
    ) {
        this.totalExpenditure = totalExpenditure;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.categoryWiseExpenses = categoryWiseExpenses;
        this.recentExpenditures = recentExpenditures;
    }

    public Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<ExpenditureItem> getRecentExpenditures() {
        return recentExpenditures;
    }

    public void setRecentExpenditures(List<ExpenditureItem> recentExpenditures) {
        this.recentExpenditures = recentExpenditures;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<CategoryWiseExpense> getCategoryWiseExpenses() {
        return categoryWiseExpenses;
    }

    public void setCategoryWiseExpenses(List<CategoryWiseExpense> categoryWiseExpenses) {
        this.categoryWiseExpenses = categoryWiseExpenses;
    }

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

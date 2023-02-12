package com.geekydroid.savestmentbackend.domain.expenditure;

import java.util.List;

public class ExpenditureOverview {

    private Double totalExpenditure;

    private Double totalExpense;

    private Double totalIncome;

    private List<ExpenditureItem> recentExpenditures;

    public ExpenditureOverview(Double totalExpenditure,
                               Double totalExpense,
                               Double totalIncome, List<ExpenditureItem> recentExpenditures) {
        this.totalExpense = totalExpense;
        this.totalExpenditure = totalExpenditure;
        this.totalIncome = totalIncome;
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

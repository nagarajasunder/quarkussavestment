package com.geekydroid.savestmentbackend.domain.expenditure;

import java.util.List;

public class ExpenditureCategoryResponse {

    private String expenditureType;

    private List<String> expenditureCategories;

    public ExpenditureCategoryResponse(String expenditureType, List<String> expenditureCategories) {
        this.expenditureType = expenditureType;
        this.expenditureCategories = expenditureCategories;
    }

    public ExpenditureCategoryResponse() {
    }

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public List<String> getExpenditureCategories() {
        return expenditureCategories;
    }

    public void setExpenditureCategories(List<String> expenditureCategories) {
        this.expenditureCategories = expenditureCategories;
    }

    public void addExpenditureCategory(String expenditureCategory) {
        if (expenditureCategories != null) {
            expenditureCategories.add(expenditureCategory);
        }
    }

    @Override
    public String toString() {
        return "ExpenditureCategoryResponse{" +
                "expenditureType='" + expenditureType + '\'' +
                ", expenditureCategories=" + expenditureCategories +
                '}';
    }
}

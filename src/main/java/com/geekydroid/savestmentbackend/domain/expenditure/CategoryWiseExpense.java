package com.geekydroid.savestmentbackend.domain.expenditure;

import javax.json.bind.annotation.JsonbProperty;

public class CategoryWiseExpense {

    @JsonbProperty("category_name")
    private String categoryName;

    @JsonbProperty("amount")
    private Double amount;

    public CategoryWiseExpense(String categoryName, Double amount) {
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CategoryWiseExpense{" +
                "categoryName='" + categoryName + '\'' +
                ", amount=" + amount +
                '}';
    }
}

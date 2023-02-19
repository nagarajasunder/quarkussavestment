package com.geekydroid.savestmentbackend.domain.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;

import javax.json.bind.annotation.JsonbProperty;

public class ExpenditureRequest {

    @JsonbProperty(value = "expenditure_type")
    private String expenditureType;
    private String expenditureDate;
    @JsonbProperty(value = "expenditure_category")
    private String expenditureCategory;
    private Double amount;
    private Paymode paymode;
    private String notes;
    private String createdBy;

    public ExpenditureRequest() {
    }

    public ExpenditureRequest(
            String expenditureType,
            String expenditureDate,
            String expenditureCategory,
            Double amount,
            Paymode paymode,
            String notes,
            String createdBy
    ) {
        this.expenditureType = expenditureType;
        this.expenditureDate = expenditureDate;
        this.expenditureCategory = expenditureCategory;
        this.amount = amount;
        this.paymode = paymode;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public String getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(String expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public String getExpenditureCategory() {
        return expenditureCategory;
    }

    public void setExpenditureCategory(String expenditureCategory) {
        this.expenditureCategory = expenditureCategory;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Paymode getPaymode() {
        return paymode;
    }

    public void setPaymode(Paymode paymode) {
        this.paymode = paymode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

package com.geekydroid.savestmentbackend.domain.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;

import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;

public class ExpenditureItem {

    @JsonbProperty(value = "exp_number")
    private String expenditureNumber;

    @JsonbProperty(value = "expenditure_date")
    private LocalDate expenditureDate;

    @JsonbProperty("expenditure_description")
    private String expenditureDescription;

    @JsonbProperty("expenditure_category")
    private String expenditureCategory;

    @JsonbProperty("expenditure_type")
    private String expenditureType;

    @JsonbProperty("expenditure_amount")
    private Double expenditureAmount;

    @JsonbProperty("pay_mode")
    private Paymode paymode;

    public ExpenditureItem(
            String expenditureNumber,
            LocalDate expenditureDate,
            String expenditureDescription,
            String expenditureCategory,
            String expenditureType,
            Double expenditureAmount,
            Paymode paymode
    ) {
        this.expenditureNumber = expenditureNumber;
        this.expenditureDate = expenditureDate;
        this.expenditureDescription = expenditureDescription;
        this.expenditureCategory = expenditureCategory;
        this.expenditureType = expenditureType;
        this.expenditureAmount = expenditureAmount;
        this.paymode = paymode;
    }

    public ExpenditureItem() {
    }

    public String getExpenditureNumber() {
        return expenditureNumber;
    }

    public void setExpenditureNumber(String expenditureNumber) {
        this.expenditureNumber = expenditureNumber;
    }

    public LocalDate getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(LocalDate expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public String getExpenditureDescription() {
        return expenditureDescription;
    }

    public void setExpenditureDescription(String expenditureDescription) {
        this.expenditureDescription = expenditureDescription;
    }

    public String getExpenditureCategory() {
        return expenditureCategory;
    }

    public void setExpenditureCategory(String expenditureCategory) {
        this.expenditureCategory = expenditureCategory;
    }

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public Double getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(Double expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }

    public Paymode getPaymode() {
        return paymode;
    }

    public void setPaymode(Paymode paymode) {
        this.paymode = paymode;
    }

    @Override
    public String toString() {
        return "ExpenditureItem{" +
                "expenditureNumber='" + expenditureNumber + '\'' +
                ", expenditureDate='" + expenditureDate + '\'' +
                ", expenditureDescription='" + expenditureDescription + '\'' +
                ", expenditureCategory='" + expenditureCategory + '\'' +
                ", expenditureType='" + expenditureType + '\'' +
                ", expenditureAmount=" + expenditureAmount +
                ", paymode=" + paymode +
                '}';
    }
}

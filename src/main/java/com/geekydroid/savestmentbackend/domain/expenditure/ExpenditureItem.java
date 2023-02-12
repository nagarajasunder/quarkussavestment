package com.geekydroid.savestmentbackend.domain.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;

public class ExpenditureItem {

    private String expenditureNumber;
    private String expenditureDate;
    private String expenditureDescription;
    private String expenditureCategory;
    private String expenditureType;
    private Double expenditureAmount;
    private Paymode paymode;

    public ExpenditureItem(
            String expenditureNumber,
            String expenditureDate,
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

    public String getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(String expenditureDate) {
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

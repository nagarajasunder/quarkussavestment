package com.geekydroid.savestmentbackend.domain.expenditure;

public class ExpenditureTypeRequest {

    private String expenditureType;

    public ExpenditureTypeRequest(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public ExpenditureTypeRequest() {
    }

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }
}

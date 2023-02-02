package com.geekydroid.domain.expenditure;

public class ExpenditureRequest {


    private String expenditureType;
    private String expenditureDate;
    private String expenditureCategory;
    private Double amount;
    private String paymode;
    private String notes;
    private String createdBy;

    public ExpenditureRequest(
            String expenditureType,
            String expenditureDate,
            String expenditureCategory,
            Double amount,
            String paymode,
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
}

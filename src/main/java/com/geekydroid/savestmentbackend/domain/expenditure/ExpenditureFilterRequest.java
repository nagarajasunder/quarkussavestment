package com.geekydroid.savestmentbackend.domain.expenditure;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class ExpenditureFilterRequest {

    @JsonbProperty("expenditure_type")
    private String expenditureType;

    @JsonbProperty("paymodes")
    private List<String> paymodes;

    @JsonbProperty(value = "from")
    private String fromDate;

    @JsonbProperty(value = "to")
    private String toDate;

    @JsonbProperty(value = "categories")
    private List<String> categories;

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public List<String> getPaymodes() {
        return paymodes;
    }

    public void setPaymodes(List<String> paymodes) {
        this.paymodes = paymodes;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ExpenditureFilterRequest{" +
                "expenditureType='" + expenditureType + '\'' +
                ", paymodes=" + paymodes +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", categories=" + categories +
                '}';
    }
}



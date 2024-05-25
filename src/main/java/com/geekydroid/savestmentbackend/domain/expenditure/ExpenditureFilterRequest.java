package com.geekydroid.savestmentbackend.domain.expenditure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class ExpenditureFilterRequest {

    private int pageNo;

    private int itemsPerPage;

    private String expenditureType;

    private List<String> paymodes;

    private String fromDate;

    private String toDate;

    private List<Long> categories;

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



package com.geekydroid.savestmentbackend.domain.expenditure;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class ExpenditureItem {
    private String expenditureNumber;
    private LocalDate expenditureDate;
    private String expenditureDescription;
    private String expenditureCategory;
    private String expenditureType;
    private Double expenditureAmount;
    private Paymode paymode;


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

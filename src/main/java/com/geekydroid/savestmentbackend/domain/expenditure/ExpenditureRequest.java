package com.geekydroid.savestmentbackend.domain.expenditure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenditureRequest {

    private String expenditureNumber;
    private String expenditureDate;
    private Long expenditureCategoryId;
    private Double amount;
    private Paymode paymode;
    private String notes;
    private String createdBy;
}

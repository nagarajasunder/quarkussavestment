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

    private String expenditureType;
    private String expenditureDate;
    private String expenditureCategory;
    private Double amount;
    private Paymode paymode;
    private String notes;
    private String createdBy;


}

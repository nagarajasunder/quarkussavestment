package com.geekydroid.savestmentbackend.domain.expenditure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenditureResponse {

    private String expNumber;
    private Long categoryId;
    private Double amount;
    private String description;
    private String paymode;
    private LocalDate date;
}

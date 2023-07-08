package com.geekydroid.savestmentbackend.domain.expenditure;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class CategoryWiseExpense {

    private String categoryName;

    private Double amount;


    @Override
    public String toString() {
        return "CategoryWiseExpense{" +
                "categoryName='" + categoryName + '\'' +
                ", amount=" + amount +
                '}';
    }
}

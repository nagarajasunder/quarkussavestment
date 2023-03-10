package com.geekydroid.savestmentbackend.domain.investment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class InvestmentTypeOverview {


    private String investmentType;

    private Double totalBuyAmount;

    private Double totalSellAmount;

    @Override
    public String toString() {
        return "InvestmentTypeOverview{" +
                "investmentType='" + investmentType + '\'' +
                ", totalBuyAmount=" + totalBuyAmount +
                ", totalSellAmount=" + totalSellAmount +
                '}';
    }
}

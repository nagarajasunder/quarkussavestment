package com.geekydroid.savestmentbackend.domain.investment;

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
public class InvestmentOverview {

    private Double totalInvestmentAmount;
    private List<InvestmentTypeOverview> investmentTypeOverview;
    private List<EquityItem> recentInvestments;



    @Override
    public String toString() {
        return "InvestmentOverview{" +
                "investmentTypeOverview=" + investmentTypeOverview +
                ", recentInvestments=" + recentInvestments +
                '}';
    }
}



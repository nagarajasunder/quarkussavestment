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
public class InvestmentPortfolioItem {
    private String assetClass;
    private Double assetAllocated;
    private Double allocationPercentage;
}

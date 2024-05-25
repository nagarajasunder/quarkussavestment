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
public class InvestmentPortfolio {
    private Double totalInvestment;
    private List<InvestmentPortfolioItem> assets;
}

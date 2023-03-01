package com.geekydroid.savestmentbackend.domain.investment;

import javax.json.bind.annotation.JsonbProperty;

public class InvestmentTypeOverview {

    @JsonbProperty(value = "investment_type")
    private String investmentType;
    @JsonbProperty("total_buy_value")
    private Double totalBuyAmount;

    @JsonbProperty(value = "total_sell_value")
    private Double totalSellAmount;

    public InvestmentTypeOverview(String investmentType, Double totalBuyAmount, Double totalSellAmount) {
        this.investmentType = investmentType;
        this.totalBuyAmount = totalBuyAmount;
        this.totalSellAmount = totalSellAmount;
    }

    public Double getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(Double totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public Double getTotalSellAmount() {
        return totalSellAmount;
    }

    public void setTotalSellAmount(Double totalSellAmount) {
        this.totalSellAmount = totalSellAmount;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    @Override
    public String toString() {
        return "InvestmentTypeOverview{" +
                "investmentType='" + investmentType + '\'' +
                ", totalBuyAmount=" + totalBuyAmount +
                ", totalSellAmount=" + totalSellAmount +
                '}';
    }
}

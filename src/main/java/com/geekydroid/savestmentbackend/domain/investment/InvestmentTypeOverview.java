package com.geekydroid.savestmentbackend.domain.investment;

public class InvestmentTypeOverview {

    private String investmentType;
    private Double totalBuyAmount;

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

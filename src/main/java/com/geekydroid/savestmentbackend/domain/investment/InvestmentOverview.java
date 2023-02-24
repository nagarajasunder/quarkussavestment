package com.geekydroid.savestmentbackend.domain.investment;

import java.util.List;

public class InvestmentOverview {

    private List<InvestmentTypeOverview> investmentTypeOverview;

    private List<EquityItem> recentInvestments;

    public InvestmentOverview(List<InvestmentTypeOverview> investmentTypeOverview, List<EquityItem> recentInvestments) {
        this.investmentTypeOverview = investmentTypeOverview;
        this.recentInvestments = recentInvestments;
    }

    public List<InvestmentTypeOverview> getInvestmentTypeOverview() {
        return investmentTypeOverview;
    }

    public void setInvestmentTypeOverview(List<InvestmentTypeOverview> investmentTypeOverview) {
        this.investmentTypeOverview = investmentTypeOverview;
    }

    public List<EquityItem> getRecentInvestments() {
        return recentInvestments;
    }

    public void setRecentInvestments(List<EquityItem> recentInvestments) {
        this.recentInvestments = recentInvestments;
    }

    @Override
    public String toString() {
        return "InvestmentOverview{" +
                "investmentTypeOverview=" + investmentTypeOverview +
                ", recentInvestments=" + recentInvestments +
                '}';
    }
}



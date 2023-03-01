package com.geekydroid.savestmentbackend.domain.investment;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class InvestmentOverview {

    @JsonbProperty("total_investment_amount")
    private Double totalInvestments;
    @JsonbProperty(value = "investment_type_overview")
    private List<InvestmentTypeOverview> investmentTypeOverview;

    @JsonbProperty(value = "recent_investments")
    private List<EquityItem> recentInvestments;

    public InvestmentOverview(Double totalInvestments,List<InvestmentTypeOverview> investmentTypeOverview, List<EquityItem> recentInvestments) {
        this.totalInvestments = totalInvestments;
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

    public Double getTotalInvestments() {
        return totalInvestments;
    }

    public void setTotalInvestments(Double totalInvestments) {
        this.totalInvestments = totalInvestments;
    }

    @Override
    public String toString() {
        return "InvestmentOverview{" +
                "investmentTypeOverview=" + investmentTypeOverview +
                ", recentInvestments=" + recentInvestments +
                '}';
    }
}



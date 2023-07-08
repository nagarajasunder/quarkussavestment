package com.geekydroid.savestmentbackend.domain.home;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureOverview;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentOverview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class HomeScreenData {

    public ExpenditureOverview expenditureOverview;

    public InvestmentOverview investmentOverview;


}

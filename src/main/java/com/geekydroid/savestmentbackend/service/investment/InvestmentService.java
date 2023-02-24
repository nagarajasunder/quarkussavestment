package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import java.util.List;

public interface InvestmentService {

    NetworkResponse addEquityItems(List<EquityItem> equityItems);

    NetworkResponse updateEquityItems(String EquityNumber,EquityItem equityItem);

    NetworkResponse deleteEquityItem(String equityNumber);

    NetworkResponse getInvestmentOverview(String startDate, String endDate);
}

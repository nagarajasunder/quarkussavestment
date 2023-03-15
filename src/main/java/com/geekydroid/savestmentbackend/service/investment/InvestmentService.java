package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentFilterRequest;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import java.io.File;
import java.util.List;

public interface InvestmentService {

    NetworkResponse addEquityItems(List<EquityItem> equityItems);

    NetworkResponse updateEquityItems(String EquityNumber,EquityItem equityItem);

    NetworkResponse deleteEquityItem(String equityNumber);

    NetworkResponse getInvestmentOverview(String startDate, String endDate);

    NetworkResponse getInvestmentItemsBasedOnGivenFilters(InvestmentFilterRequest filterRequest);

    File exportDataToExcel(InvestmentFilterRequest request);
}

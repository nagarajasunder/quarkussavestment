package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentFilterRequest;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentPortfolio;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import java.io.File;
import java.util.List;

public interface InvestmentService {

    EquityItem getById(String investmentNumber);

    EquityItem create(EquityItem item);

    NetworkResponse addEquityItems(List<EquityItem> equityItems,String userId);

    EquityItem update(EquityItem equityItem);

    EquityItem delete(String equityNumber);

    NetworkResponse getInvestmentOverview(String startDate, String endDate,String userId);

    NetworkResponse getInvestmentItemsBasedOnGivenFilters(InvestmentFilterRequest filterRequest,String userId);

    File exportDataToExcel(InvestmentFilterRequest request,String userId);

    InvestmentPortfolio getInvestmentPortfolio(String userId);
}

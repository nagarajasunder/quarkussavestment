package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.*;

import java.time.LocalDate;
import java.util.List;

public interface InvestmentRepository {

    List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems);

    InvestmentItem updateEquity(String equityNumber, EquityItem equityItem, InvestmentType investmentType);

    void deleteEquity(String equityNumber);

    List<InvestmentItem> getAllEquityItems();

    InvestmentItem findInvestmentItemById(String investmentNumber);

    List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(LocalDate startDate, LocalDate endDate,String userId);

    List<EquityItem> getEquityItemsBasedOnGivenFilters(
            String equityId,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<String> investmentCategories,
            String tradeType,
            int limit
    );
}

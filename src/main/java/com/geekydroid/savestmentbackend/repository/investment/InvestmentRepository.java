package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.*;

import java.time.LocalDate;
import java.util.List;

public interface InvestmentRepository {

    List<InvestmentItem> bulkCreate(List<InvestmentItem> investmentItems);

    InvestmentItem create(InvestmentItem item);

    InvestmentItem getById(String id);


    InvestmentItem update(InvestmentItem investmentItem);

    InvestmentItem delete(InvestmentItem investmentItem);

    List<InvestmentItem> getAllEquityItems();

    InvestmentItem findInvestmentItemById(String investmentNumber);

    List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(LocalDate startDate, LocalDate endDate,String userId);

    List<EquityItem> getEquityItemsBasedOnGivenFilters(
            String equityId,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<Long> investmentCategories,
            String tradeType,
            int pageNo,
            int itemsPerPage
    );

    EquityItem getByNumber(String investmentNumber);

    List<InvestmentPortfolioItem> getInvestmentPortfolio(String userId);
}

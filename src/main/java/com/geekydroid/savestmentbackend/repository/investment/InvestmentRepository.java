package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import java.util.List;

public interface InvestmentRepository {

    List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems);

    InvestmentItem updateEquity(String equityNumber, EquityItem equityItem, InvestmentType investmentType);

    void deleteEquity(String equityNumber);

    List<InvestmentItem> getAllEquityItems();

    InvestmentItem findInvestmentItemById(String investmentNumber);

}

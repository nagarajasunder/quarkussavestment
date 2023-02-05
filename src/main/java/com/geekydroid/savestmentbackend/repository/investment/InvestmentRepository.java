package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;

import java.util.List;

public interface InvestmentRepository {

    List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems);

}

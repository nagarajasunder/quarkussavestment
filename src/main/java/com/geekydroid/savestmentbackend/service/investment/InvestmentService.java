package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;

import java.util.List;

public interface InvestmentService {

    void addEquityItems(List<EquityItem> equityItems);

}

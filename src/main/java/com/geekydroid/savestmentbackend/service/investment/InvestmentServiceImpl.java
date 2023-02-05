package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentServiceImpl implements InvestmentService {

    @Inject
    InvestmentRepository investmentRepository;

    @Override
    public void addEquityItems(List<EquityItem> equityItems) {
        List<InvestmentItem> investmentItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (EquityItem equityItem : equityItems) {
            InvestmentItem investmentItem = new InvestmentItem(
                    null,
                    equityItem.getSymbol(),
                    equityItem.getTradeDate(),
                    equityItem.getTradeType(),
                    equityItem.getQuantity(),
                    equityItem.getPrice(),
                    equityItem.getAmountInvested(),
                    null,
                    now,
                    now
            );
            investmentItems.add(investmentItem);
        }
        List<InvestmentItem> investmentItemList = investmentRepository.addEquity(investmentItems);
    }
}

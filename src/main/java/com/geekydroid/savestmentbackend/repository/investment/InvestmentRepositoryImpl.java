package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentRepositoryImpl implements InvestmentRepository{


    @Inject
    EntityManager entityManager;

    @Override
    public List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems) {
        InvestmentItem.persist(investmentItems);
        return investmentItems;
    }

    @Override
    public InvestmentItem updateEquity(String equityNumber, EquityItem equityItem, InvestmentType investmentType) {
        InvestmentItem entity = InvestmentItem.findById(equityNumber);
        if (entity == null) {
            throw new NotFoundException("Equity with equity number "+equityNumber+" does not exist");
        }
        entity.setInvestmentTypes(investmentType);
        entity.setSymbol(equityItem.getSymbol());
        entity.setUnits(equityItem.getQuantity());
        entity.setPrice(equityItem.getPrice());
        entity.setAmountInvested(equityItem.getAmountInvested());
        entity.setUpdatedOn(equityItem.getUpdatedOn());
        entity.setTradeType(equityItem.getTradeType());
        entity.setTradeDate(equityItem.getTradeDate());
        return entity;

    }

    @Override
    public void deleteEquity(String investmentNumber) {
        InvestmentItem entity = InvestmentItem.findById(investmentNumber);
        if (entity == null) {
            throw new NotFoundException("Equity with equity number "+investmentNumber+" does not exist");
        }
        entity.delete();
    }

    @Override
    public List<InvestmentItem> getAllEquityItems() {
        return InvestmentItem.listAll();
    }

    @Override
    public InvestmentItem findInvestmentItemById(String investmentNumber) {
        return InvestmentItem.findById(investmentNumber);
    }


}

package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentRepositoryImpl implements InvestmentRepository{


    @Inject
    EntityManager entityManager;

    @Override
    public List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems) {
        for (InvestmentItem investmentItem : investmentItems) {
            entityManager.persist(investmentItem);
        }
        return investmentItems;
    }
}

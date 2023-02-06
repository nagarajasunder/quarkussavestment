package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentTypeRepositoryImpl implements InvestmentTypeRepository{


    @Override
    public InvestmentType findInvestmentTypeByName(String investmentTypeName) {
        return InvestmentType.find("investmentName",investmentTypeName).firstResult();
    }

    @Override
    public List<InvestmentType> getAllInvestmentTypes() {
        return InvestmentType.listAll();
    }
}

package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentTypeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentTypeServiceImpl implements InvestmentTypeService{

    @Inject
    InvestmentTypeRepository repository;


    @Override
    public InvestmentType findInvestmentTypeByName(String investmentTypeName) {
        return repository.findInvestmentTypeByName(investmentTypeName);
    }

    @Override
    public List<InvestmentType> getAllInvestmentTypes() {
        return repository.getAllInvestmentTypes();
    }
}

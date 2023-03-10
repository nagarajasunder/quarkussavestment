package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import java.util.List;

public interface InvestmentTypeRepository {

    InvestmentType findInvestmentTypeByName(String investmentTypeName);

    List<InvestmentType> getAllInvestmentTypes();

    List<String> getAllInvestmentCategories();
}

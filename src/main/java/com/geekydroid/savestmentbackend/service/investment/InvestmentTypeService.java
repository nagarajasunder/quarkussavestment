package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import java.util.List;

public interface InvestmentTypeService {

    InvestmentType findInvestmentTypeByName(String investmentTypeName);

    List<InvestmentType> getAllInvestmentTypes();
}

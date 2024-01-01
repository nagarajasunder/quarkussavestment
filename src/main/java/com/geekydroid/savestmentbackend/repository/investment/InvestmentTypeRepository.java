package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import java.util.List;

public interface InvestmentTypeRepository {

    InvestmentType getById(Long id);

    List<CategoryRespnose> getAllInvestmentCategories();

    InvestmentType create(InvestmentType investmentType);

    InvestmentType update(InvestmentType investmentType);

    InvestmentType delete(InvestmentType investmentType);

    InvestmentType getByName(String name);
}

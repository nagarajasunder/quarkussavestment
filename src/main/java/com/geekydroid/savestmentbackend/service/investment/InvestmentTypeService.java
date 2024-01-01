package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.expenditure.UpdateCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.CreateInvestmentCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;

import java.util.List;

public interface InvestmentTypeService {

    List<CategoryRespnose> getAllInvestmentCategories();

    CategoryRespnose create(CreateInvestmentCategoryRequest request);

    CategoryRespnose update(UpdateCategoryRequest request);

    CategoryRespnose delete(Long id);

}

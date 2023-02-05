package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureRequest;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    NetworkResponse createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory);

    NetworkResponse updateExpenditure(String expNumber, ExpenditureRequest expenditureRequest);

    NetworkResponse deleteExpenditure(String expNumber);



}

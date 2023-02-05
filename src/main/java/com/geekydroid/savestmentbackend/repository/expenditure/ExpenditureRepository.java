package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public interface ExpenditureRepository {

    Expenditure createExpenditure(Expenditure expenditure);

    Expenditure updateExpenditure(String expNumber, Expenditure expenditure);

    Expenditure deleteExpenditure(String expNumber);

    Expenditure getExpenditureByExpNumber(String expNumber);
}

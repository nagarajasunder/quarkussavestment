package com.geekydroid.repository.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class ExpenditureRepositoryImpl  implements ExpenditureRepository{

    @Inject
    EntityManager entityManager;

    @Override
    public void createExpenditure(Expenditure expenditure) {

    }

    @Override
    public void updateExpenditure(Expenditure expenditure) {

    }

    @Override
    public void deleteExpenditure(Expenditure expenditure) {

    }
}

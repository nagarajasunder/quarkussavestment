package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ExpenditureRepositoryImpl  implements ExpenditureRepository{

    @Inject
    EntityManager entityManager;

    @Override
    public Expenditure createExpenditure(Expenditure expenditure) {
        entityManager.persist(expenditure);
        return expenditure;

    }

    @Override
    public Expenditure updateExpenditure(String expNumber, Expenditure expenditure) {
        return entityManager.merge(expenditure);

    }

    @Override
    public Expenditure deleteExpenditure(String expNumber) {
        Expenditure expenditure = entityManager.find(Expenditure.class,expNumber);
        if (expenditure == null) {
            return null;
        } else {
            entityManager.remove(expenditure);
            return expenditure;
        }

    }

    @Override
    public Expenditure getExpenditureByExpNumber(String expNumber) {
        return entityManager.find(Expenditure.class,expNumber);
    }
}

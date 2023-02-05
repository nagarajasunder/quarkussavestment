package com.geekydroid.repository.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;

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
    public void updateExpenditure(Expenditure expenditure) {

    }

    @Override
    public void deleteExpenditure(Expenditure expenditure) {

    }
}

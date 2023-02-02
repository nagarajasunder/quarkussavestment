package com.geekydroid.repository.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public interface ExpenditureRepository {

    void createExpenditure(Expenditure expenditure);

    void updateExpenditure(Expenditure expenditure);

    void deleteExpenditure(Expenditure expenditure);
}

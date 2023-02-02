package com.geekydroid.service.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    void createExpenditure(Expenditure expenditure);

    void updateExpenditure(Expenditure expenditure);

    void deleteExpenditure(Expenditure expenditure);



}

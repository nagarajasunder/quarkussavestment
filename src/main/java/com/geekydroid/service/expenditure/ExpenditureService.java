package com.geekydroid.service.expenditure;

import com.geekydroid.domain.expenditure.Expenditure;
import com.geekydroid.domain.expenditure.ExpenditureCategory;
import com.geekydroid.domain.expenditure.ExpenditureRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    ExpenditureRequest createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory);

    void updateExpenditure(Expenditure expenditure);

    void deleteExpenditure(Expenditure expenditure);



}

package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;

import java.util.List;

public interface ExpenditureTypeRepository {

    List<ExpenditureType> addExpenditureType(List<ExpenditureType> expenditureTypes);

    List<ExpenditureType> getAllExpenditureTypes();

}

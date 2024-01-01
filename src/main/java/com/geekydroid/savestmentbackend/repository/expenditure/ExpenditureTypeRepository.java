package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeResponse;

import java.util.List;

public interface ExpenditureTypeRepository {

    List<ExpenditureType> addExpenditureType(List<ExpenditureType> expenditureTypes);

    List<ExpenditureTypeResponse> getAllExpenditureTypes();

    ExpenditureType getById(Long id);

}

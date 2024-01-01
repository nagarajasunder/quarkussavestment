package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeRequest;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import java.util.List;

public interface ExpenditureTypeService {

   NetworkResponse addExpenditureType(List<ExpenditureTypeRequest> expenditureTypeStr);

    List<ExpenditureTypeResponse> getAllExpenditureTypes();
}

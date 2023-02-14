package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    NetworkResponse createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory);

    NetworkResponse updateExpenditure(String expNumber, ExpenditureRequest expenditureRequest);

    NetworkResponse deleteExpenditure(String expNumber);

    ExpenditureOverview getExpenditureOverview(String startDate,String endDate);

    List<ExpenditureItem> getExpenditureItemsGivenDateRange(String startDate,String endDate);


    NetworkResponse getExpenditureByExpNumber(String expNumber);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String expenditureType,
            Paymode paymode,
            String fromDate,
            String toDate,
            String expenditureCategoryStr
    );
}

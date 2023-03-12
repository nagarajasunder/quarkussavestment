package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    NetworkResponse createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory);

    NetworkResponse updateExpenditure(String expNumber, ExpenditureRequest expenditureRequest);

    NetworkResponse deleteExpenditure(String expNumber);

    ExpenditureOverview getExpenditureOverview(String startDate,String endDate);

    List<ExpenditureItem> getExpenditureItemsGivenDateRange(String startDate,String endDate,int limit);


    NetworkResponse getExpenditureByExpNumber(String expNumber);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            ExpenditureFilterRequest request
    );

    NetworkResponse getCategoryWiseExpenseByGivenDateRange(String startDate, String endDate);

    File exportDataToExcel(ExpenditureFilterRequest filterRequest) throws IOException;
}

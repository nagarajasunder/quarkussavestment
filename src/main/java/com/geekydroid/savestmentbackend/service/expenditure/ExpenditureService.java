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

    NetworkResponse createExpenditure(String userId,ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory);

    NetworkResponse updateExpenditure(String expNumber, ExpenditureRequest expenditureRequest);

    NetworkResponse deleteExpenditure(String expNumber);

    ExpenditureOverview getExpenditureOverview(String usesrId,String startDate,String endDate);

    List<ExpenditureItem> getExpenditureItemsGivenDateRange(String userId,String startDate,String endDate,int limit);


    NetworkResponse getExpenditureByExpNumber(String expNumber);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String userId,
            ExpenditureFilterRequest request
    );

    NetworkResponse getCategoryWiseExpenseByGivenDateRange(String startDate, String endDate,String userId);

    File exportDataToExcel(ExpenditureFilterRequest filterRequest,String userId) throws IOException;

    void deleteExpenditureByCategoryName(List<String> categoryNames);
}

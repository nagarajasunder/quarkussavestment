package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
@Transactional
public interface ExpenditureService {

    ExpenditureResponse create(ExpenditureRequest expenditureRequest);

    ExpenditureResponse update(ExpenditureRequest expenditureRequest);

    ExpenditureResponse delete(String expNumber);

    ExpenditureOverview getExpenditureOverview(String usesrId,String startDate,String endDate);

    NetworkResponse getExpenditureByExpNumber(String expNumber);

    List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            String userId,
            ExpenditureFilterRequest request
    );

    NetworkResponse getCategoryWiseExpenseByGivenDateRange(String startDate, String endDate,String userId);

    File exportDataToExcel(ExpenditureFilterRequest filterRequest,String userId) throws IOException;
}

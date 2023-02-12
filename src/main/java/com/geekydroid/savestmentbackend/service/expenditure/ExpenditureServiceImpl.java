package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.Expenditure;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureCategory;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureOverview;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureRequest;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;
import com.geekydroid.utils.DateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class ExpenditureServiceImpl implements ExpenditureService {

    @Inject
    ExpenditureRepository repository;
    @Inject
    ExpenditureCategoryService expenditureCategoryService;

    @Override
    public NetworkResponse createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory) {
        LocalDateTime now = LocalDateTime.now();
        Expenditure newExpenditure = new Expenditure(
                expenditureCategory,
                expenditureRequest.getAmount(),
                expenditureRequest.getNotes(),
                expenditureRequest.getPaymode(),
                DateUtils.fromStringToDateTime(expenditureRequest.getExpenditureDate()),
                UUID.fromString(expenditureRequest.getCreatedBy()),
                now,
                now
        );
        Expenditure expenditure = repository.createExpenditure(newExpenditure);
        if (expenditure != null) {
            return new Success(Response.Status.CREATED, null, expenditureRequest);
        }
        return null;
    }

    @Override
    public NetworkResponse updateExpenditure(String expNumber, ExpenditureRequest expenditureRequest) {
        Expenditure expenditure = repository.getExpenditureByExpNumber(expNumber);
        if (expenditure == null) {
            return new Exception(Response.Status.BAD_REQUEST, new java.lang.Exception("The given expenditure number " + expNumber + " doesn't exist"), null);
        }
        if (expenditureRequest.getAmount() != null) {
            expenditure.setExpenditureAmount(expenditureRequest.getAmount());
        }
        if (expenditureRequest.getExpenditureDate() != null) {
            expenditure.setExpenditureDate(DateUtils.fromStringToDateTime(expenditureRequest.getExpenditureDate()));
        }
        if (expenditureRequest.getExpenditureCategory() != null && !expenditureRequest.getExpenditureCategory().isEmpty()) {
            ExpenditureCategory category = expenditureCategoryService.getExpenditureCategoryByName(expenditureRequest.getExpenditureCategory());
            if (category != null) {
                expenditure.setExpenditureCategory(category);
            } else {
                return new Exception(Response.Status.BAD_REQUEST, new java.lang.Exception("Invalid expenditure category"), null);
            }
        }
        if (expenditureRequest.getNotes() != null) {
            expenditure.setExpenditureDescription(expenditureRequest.getNotes());
        }
        if (expenditureRequest.getPaymode() != null) {
            expenditure.setPaymode(expenditureRequest.getPaymode());
        }
        Expenditure updatedExpenditure = repository.updateExpenditure(expNumber, expenditure);
        if (updatedExpenditure != null) {
            return new Success(Response.Status.OK, null, updatedExpenditure);
        }
        return new Success(Response.Status.INTERNAL_SERVER_ERROR, null, null);
    }

    @Override
    public NetworkResponse deleteExpenditure(String expNumber) {
        Expenditure expenditure = repository.deleteExpenditure(expNumber);
        if (expenditure != null) {
            GenericNetworkResponse response = new GenericNetworkResponse();
            response.setStatus("SUCCESS");
            response.setMessage("Expenditure Deleted Successfully");
            response.setStatusCode(Response.Status.OK.getStatusCode());
            return new Success(Response.Status.OK, null, response);
        }
        return new com.geekydroid.savestmentbackend.utils.models.Exception(
                Response.Status.BAD_REQUEST,
                new java.lang.Exception("The expenditure with expenditure number " + expNumber + " does not exist"),
                null
        );

    }

    @Override
    public ExpenditureOverview getExpenditureOverview(String startDate,String endDate) {

        List<Double> totalExpenditures = repository.getTotalExpenseAndIncomeAmount(startDate,endDate);

        System.out.println(totalExpenditures);

        return null;
    }
}

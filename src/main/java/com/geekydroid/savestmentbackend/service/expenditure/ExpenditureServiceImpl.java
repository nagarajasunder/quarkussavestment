package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        //Todo("To be removed")
        expenditureRequest.setCreatedBy(UUID.randomUUID().toString());
        Expenditure newExpenditure = new Expenditure(
                expenditureCategory,
                expenditureRequest.getAmount(),
                expenditureRequest.getNotes(),
                expenditureRequest.getPaymode(),
                DateUtils.fromStringToLocalDate(expenditureRequest.getExpenditureDate()),
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
            expenditure.setExpenditureDate(DateUtils.fromStringToLocalDate(expenditureRequest.getExpenditureDate()));
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
    public ExpenditureOverview getExpenditureOverview(String startDate, String endDate) {

        LocalDate startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate endLocalDate = DateUtils.fromStringToLocalDate(endDate);

        List<Double> totalExpenditures = repository.getTotalExpenseAndIncomeAmount(startLocalDate, endLocalDate);
        List<ExpenditureItem> expenditureItems = getExpenditureItemsGivenDateRange(startDate, endDate);

        Double totalExpenditure = totalExpenditures.get(0) + totalExpenditures.get(1);

        return new ExpenditureOverview(
                totalExpenditure,
                totalExpenditures.get(0),
                totalExpenditures.get(1),
                expenditureItems

        );
    }

    @Override
    public List<ExpenditureItem> getExpenditureItemsGivenDateRange(String startDate, String endDate) {

        LocalDate startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate endLocalDate = DateUtils.fromStringToLocalDate(endDate);

        return repository.getExpenditureByGivenDateRange(startLocalDate, endLocalDate);
    }

    @Override
    public NetworkResponse getExpenditureByExpNumber(String expNumber) {
        if (expNumber.isEmpty()) {
            return new com.geekydroid.savestmentbackend.utils.models.Error(
                    Response.Status.BAD_REQUEST,
                    new java.lang.Exception("The expenditure number cannot be empty"),
                    null
            );
        }
        Expenditure expenditure = repository.getExpenditureByExpNumber(expNumber);
        if (expenditure == null) {
            return new com.geekydroid.savestmentbackend.utils.models.Error(
                    Response.Status.BAD_REQUEST,
                    new NotFoundException("The expenditure with expenditure number " + expNumber + " does not exist"),
                    null
            );
        }
        ExpenditureItem expenditureItem = new ExpenditureItem(
                expenditure.getExpenditureNumber(),
                expenditure.getExpenditureDate(),
                expenditure.getExpenditureDescription(),
                expenditure.getExpenditureCategory().getCategoryName(),
                expenditure.getExpenditureCategory().getExpenditureType().getExpenditureName(),
                expenditure.getExpenditureAmount(),
                expenditure.getPaymode()

        );
        return new Success(Response.Status.OK, null, expenditureItem);
    }

    @Override
    public List<ExpenditureItem> getExpenditureItemBasedOnGivenFilters(
            ExpenditureFilterRequest request
    ) {
        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;
        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            startLocalDate = DateUtils.fromStringToLocalDate(request.getFromDate());
        }

        if (request.getToDate() != null && !request.getToDate().isEmpty()) {
            endLocalDate = DateUtils.fromStringToLocalDate(request.getToDate());
        }

        List<Paymode> paymodes = new ArrayList<>();
        if (request.getPaymodes() != null && request.getPaymodes().size() > 0) {
            paymodes = request.getPaymodes().stream().map(paymode -> Paymode.valueOf(paymode.toUpperCase())).toList();
        }


        return repository.getExpenditureItemBasedOnGivenFilters(
                request.getExpenditureType(),
                paymodes,
                startLocalDate,
                endLocalDate,
                request.getCategories()
        );
    }
}

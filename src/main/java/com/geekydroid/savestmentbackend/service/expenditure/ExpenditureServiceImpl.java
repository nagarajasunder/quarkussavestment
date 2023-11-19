package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.ExpenditurePdfGenerator;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class ExpenditureServiceImpl implements ExpenditureService {

    @Inject
    ExpenditureRepository repository;
    @Inject
    ExpenditureCategoryService expenditureCategoryService;

    @Override
    public NetworkResponse createExpenditure(String userId,ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory) {
        LocalDateTime now = LocalDateTime.now();
        Expenditure newExpenditure = new Expenditure(
                expenditureCategory,
                expenditureRequest.getAmount(),
                expenditureRequest.getNotes(),
                expenditureRequest.getPaymode(),
                DateUtils.fromStringToLocalDate(expenditureRequest.getExpenditureDate()),
                userId,
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
    @Transactional
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
        expenditure.setUpdatedOn(LocalDateTime.now());
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
    public ExpenditureOverview getExpenditureOverview(String userId,String startDate, String endDate) {

        LocalDate startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate endLocalDate = DateUtils.fromStringToLocalDate(endDate);

        List<Double> totalExpenditures = repository.getTotalExpenseAndIncomeAmount(userId,startLocalDate, endLocalDate);
        List<CategoryWiseExpense> categoryWiseExpenses = repository.getCategoryWiseExpenseByGivenDateRange(userId,startLocalDate,endLocalDate);

        Double balanceAmount = totalExpenditures.get(1) - totalExpenditures.get(0);

        return new ExpenditureOverview(
                balanceAmount,
                totalExpenditures.get(0),
                totalExpenditures.get(1),
                categoryWiseExpenses

        );
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
            String userId,
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
        if (request.getPaymodes() != null && !request.getPaymodes().isEmpty()) {
            paymodes = request.getPaymodes().stream().map(paymode -> Paymode.valueOf(paymode.toUpperCase())).toList();
        }


        return repository.getExpenditureItemBasedOnGivenFilters(
                request.getExpenditureType(),
                paymodes,
                startLocalDate,
                endLocalDate,
                userId,
                request.getCategories(),
                request.getPageNo(),
                request.getItemsPerPage()
        );
    }

    @Override
    public NetworkResponse getCategoryWiseExpenseByGivenDateRange(String startDate, String endDate,String userId) {
        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;
        if (startDate != null && !startDate.isEmpty()) {
            startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        }

        if (endDate != null && !endDate.isEmpty()) {
            endLocalDate = DateUtils.fromStringToLocalDate(endDate);
        }

        List<CategoryWiseExpense> categoryWiseExpenses = repository.getCategoryWiseExpenseByGivenDateRange(userId,startLocalDate,endLocalDate);

        return new Success(Response.Status.OK,null,categoryWiseExpenses);
    }

    @Override
    public File exportDataToExcel(ExpenditureFilterRequest request,String userId) {

        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;
        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            startLocalDate = DateUtils.fromStringToLocalDate(request.getFromDate());
        }

        if (request.getToDate() != null && !request.getToDate().isEmpty()) {
            endLocalDate = DateUtils.fromStringToLocalDate(request.getToDate());
        }

        List<Paymode> paymodes = new ArrayList<>();
        if (request.getPaymodes() != null && !request.getPaymodes().isEmpty()) {
            paymodes = request.getPaymodes().stream().map(paymode -> Paymode.valueOf(paymode.toUpperCase())).toList();
        }


        List<ExpenditureItem> expenditureItems =  repository.getExpenditureItemBasedOnGivenFilters(
                request.getExpenditureType(),
                paymodes,
                startLocalDate,
                endLocalDate,
                userId,
                request.getCategories(),
                0,
                0
        );

        //ExpenditureExcelGenerator generator = new ExpenditureExcelGenerator();
        ExpenditurePdfGenerator generator = new ExpenditurePdfGenerator();
        return generator.createPdf(startLocalDate,endLocalDate,expenditureItems);

    }

    @Override
    @Transactional
    public void deleteExpenditureByCategoryName(List<String> categoryNames) {
        List<String> expenditureToBeDeleted = repository.getExpenditureNumberFromCategoryName(categoryNames);
        if (expenditureToBeDeleted != null && !expenditureToBeDeleted.isEmpty()) {
            Expenditure.deleteExpenditureByExpNumber(expenditureToBeDeleted);
        }
        ExpenditureCategory.deleteExpenditureCategoryByName(categoryNames);
    }
}

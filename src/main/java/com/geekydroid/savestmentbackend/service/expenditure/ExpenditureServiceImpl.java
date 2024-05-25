package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureCategoryRepository;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureTypeRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.ExpenditurePdfGenerator;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
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
    ExpenditureTypeRepository expenditureTypeRepository;

    @Inject
    ExpenditureCategoryRepository expenditureCategoryRepository;

    @Override
    public ExpenditureResponse create(ExpenditureRequest request) {

        if (
                request.getAmount() == null ||
                        request.getExpenditureDate() == null ||
                        request.getExpenditureDate().isEmpty() ||
                        request.getExpenditureCategoryId() == null
        ) {
            throw new BadRequestException("Invalid request");
        }

        ExpenditureCategory category = expenditureCategoryRepository.getById(request.getExpenditureCategoryId());
        if (category == null) {
            throw new BadRequestException("Expenditure category doesn't exist");
        }

        LocalDateTime now = LocalDateTime.now();
        Expenditure expenditure = new Expenditure(category);
        expenditure.setExpenditureAmount(request.getAmount());
        expenditure.setExpenditureDescription(request.getNotes());
        expenditure.setPaymode(request.getPaymode());
        expenditure.setExpenditureDate(DateUtils.fromStringToLocalDate(request.getExpenditureDate()));
        expenditure.setCreatedBy(request.getCreatedBy());
        expenditure.setCreatedOn(now);
        expenditure.setUpdatedOn(now);
        expenditure = repository.create(expenditure);
        return new ExpenditureResponse(
                expenditure.getExpenditureNumber(),
                expenditure.getExpenditureCategory().getExpenditureCategoryId(),
                expenditure.getExpenditureAmount(),
                expenditure.getExpenditureDescription(),
                expenditure.getPaymode().name(),
                expenditure.getExpenditureDate()
        );
    }

    @Override
    @Transactional
    public ExpenditureResponse update(ExpenditureRequest request) {
        if (
                request.getExpenditureNumber() == null ||
                        request.getExpenditureNumber().isEmpty() ||
                        request.getExpenditureCategoryId() == null ||
                        request.getAmount() == null ||
                        request.getPaymode() == null ||
                        request.getExpenditureDate() == null
        ) {
            throw new BadRequestException("Invalid request");
        }
        Expenditure expenditure = repository.getExpenditureByExpNumber(request.getExpenditureNumber());
        if (expenditure == null) {
           throw new BadRequestException("The given expenditure number " + request.getExpenditureNumber() + " doesn't exist");
        }

        ExpenditureCategory category = expenditureCategoryRepository.getById(request.getExpenditureCategoryId());

        if (category == null) {
            throw new BadRequestException("Invalid category");
        }

        expenditure.setExpenditureAmount(request.getAmount());
        expenditure.setExpenditureDate(DateUtils.fromStringToLocalDate(request.getExpenditureDate()));
        expenditure.setExpenditureCategory(category);
        expenditure.setExpenditureDescription(request.getNotes());
        expenditure.setPaymode(request.getPaymode());
        expenditure.setUpdatedOn(LocalDateTime.now());
        Expenditure updatedExpenditure = repository.update(expenditure);
        return new ExpenditureResponse(
                updatedExpenditure.getExpenditureNumber(),
                updatedExpenditure.getExpenditureCategory().getExpenditureCategoryId(),
                updatedExpenditure.getExpenditureAmount(),
                updatedExpenditure.getExpenditureDescription(),
                updatedExpenditure.getPaymode().name(),
                updatedExpenditure.getExpenditureDate()
        );
    }

    @Override
    public ExpenditureResponse delete(String expNumber) {

        if (expNumber == null) {
            throw new BadRequestException("Invalid request");
        }

        Expenditure expenditure = repository.getExpenditureByExpNumber(expNumber);

        if (expenditure == null) {
            throw new BadRequestException("Expenditure doesn't exist");
        }

        repository.delete(expenditure);

        return new ExpenditureResponse(
                expenditure.getExpenditureNumber(),
                expenditure.getExpenditureCategory().getExpenditureCategoryId(),
                expenditure.getExpenditureAmount(),
                expenditure.getExpenditureDescription(),
                expenditure.getPaymode().name(),
                expenditure.getExpenditureDate()
        );
    }

    @Override
    public ExpenditureOverview getExpenditureOverview(String userId, String startDate, String endDate) {

        LocalDate startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate endLocalDate = DateUtils.fromStringToLocalDate(endDate);

        List<Double> totalExpenditures = repository.getTotalExpenseAndIncomeAmount(userId, startLocalDate, endLocalDate);
        List<CategoryWiseExpense> categoryWiseExpenses = repository.getCategoryWiseExpenseByGivenDateRange(userId, startLocalDate, endLocalDate);

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
                expenditure.getExpenditureCategory().getExpenditureCategoryId(),
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
    public NetworkResponse getCategoryWiseExpenseByGivenDateRange(String startDate, String endDate, String userId) {
        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;
        if (startDate != null && !startDate.isEmpty()) {
            startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        }

        if (endDate != null && !endDate.isEmpty()) {
            endLocalDate = DateUtils.fromStringToLocalDate(endDate);
        }

        List<CategoryWiseExpense> categoryWiseExpenses = repository.getCategoryWiseExpenseByGivenDateRange(userId, startLocalDate, endLocalDate);

        return new Success(Response.Status.OK, null, categoryWiseExpenses);
    }

    @Override
    public File exportDataToExcel(ExpenditureFilterRequest request, String userId) {

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


        List<ExpenditureItem> expenditureItems = repository.getExpenditureItemBasedOnGivenFilters(
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
        return generator.createPdf(startLocalDate, endLocalDate, expenditureItems);

    }
}

package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.*;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentTypeRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.InvestmentExcelGenerator;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
@Transactional
public class InvestmentServiceImpl implements InvestmentService {

    @Inject
    InvestmentRepository investmentRepository;

    @Inject
    InvestmentTypeRepository investmentTypeRepository;

    @Override
    public NetworkResponse addEquityItems(List<EquityItem> equityItems) {
        List<InvestmentItem> investmentItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (EquityItem equityItem : equityItems) {
            InvestmentType investmentType = investmentTypeRepository.findInvestmentTypeByName(equityItem.getInvestmentType());
            if (investmentType != null) {
                InvestmentItem investmentItem = new InvestmentItem(
                        investmentType,
                        equityItem.getSymbol(),
                        equityItem.getTradeDate(),
                        equityItem.getTradeType().toUpperCase(),
                        equityItem.getQuantity(),
                        equityItem.getPrice(),
                        equityItem.getAmountInvested(),
                        equityItem.getCreatedBy(),
                        now,
                        now
                );
                investmentItems.add(investmentItem);
            }
        }
        System.out.println("Investment Items size " + investmentItems.size());
        if (investmentItems.size() > 0) {
            System.out.println("INvestment Items " + investmentItems);
            List<InvestmentItem> investmentItemList = investmentRepository.addEquity(investmentItems);
            if (investmentItemList != null && investmentItemList.size() > 0) {
                return new Success(Response.Status.CREATED, null, new GenericNetworkResponse(
                        Response.Status.CREATED.getStatusCode(),
                        "success",
                        "Equity Item created successfully",
                        null

                ));
            }
        }
        return new Error(Response.Status.INTERNAL_SERVER_ERROR, null, null);
    }

    @Override
    public NetworkResponse updateEquityItems(String EquityNumber, EquityItem equityItem) {
        GenericNetworkResponse errorResp = new GenericNetworkResponse();
        InvestmentType investmentType;
        if (EquityNumber == null || EquityNumber.isEmpty()) {
            errorResp.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            errorResp.setMessage("Equity number should not be empty");
            errorResp.setStatus("FAILED");
            return new Error(Response.Status.BAD_REQUEST, null, errorResp);
        } else {
            InvestmentItem item = investmentRepository.findInvestmentItemById(EquityNumber);
            if (item == null) {
                errorResp.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                errorResp.setMessage("Equity with number " + EquityNumber + " doesn't exist.");
                errorResp.setStatus("FAILED");
                return new Error(Response.Status.BAD_REQUEST, null, errorResp);
            }
            investmentType = investmentTypeRepository.findInvestmentTypeByName(equityItem.getInvestmentType());
            if (investmentType == null) {
                return new Error(Response.Status.BAD_REQUEST, null, "Invalid investment type");
            }
        }
        InvestmentItem item = investmentRepository.updateEquity(EquityNumber, equityItem, investmentType);
        if (item == null) {
            return new Error(Response.Status.INTERNAL_SERVER_ERROR, null, null);
        }
        return new Success(Response.Status.OK, null, new GenericNetworkResponse(
                Response.Status.OK.getStatusCode(),
                "success",
                "Equity Item updated successfully",
                null
        ));
    }


    @Override
    public NetworkResponse deleteEquityItem(String equityNumber) {
        try {
            investmentRepository.deleteEquity(equityNumber);
        } catch (NotFoundException exception) {
            return new Exception(Response.Status.BAD_REQUEST, exception, null);
        }
        return new Success(Response.Status.OK, null, "Equity with equity number " + equityNumber + " deleted successfully");
    }

    @Override
    public NetworkResponse getInvestmentOverview(String startDate, String endDate) {
        LocalDate localStartDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate localEndDate = DateUtils.fromStringToLocalDate(endDate);

        List<InvestmentTypeOverview> overviews = investmentRepository.getTotalInvestmentItemsByTypeGivenDateRange(localStartDate, localEndDate);
        List<EquityItem> recentEquityData = investmentRepository.getEquityItemsBasedOnGivenFilters(null, localStartDate, localEndDate, null, null,5);
        AtomicReference<Double> totalInvestmentAmount = new AtomicReference<>(0.0);
        overviews.forEach(item -> totalInvestmentAmount.updateAndGet(v -> v + item.getTotalBuyAmount()));
        InvestmentOverview investmentOverview = new InvestmentOverview(totalInvestmentAmount.get(), overviews, recentEquityData);
        System.out.println("getInvestmentOverview " + investmentOverview);
        return new Success(Response.Status.OK, null, investmentOverview);

    }

    @Override
    public NetworkResponse getInvestmentItemsBasedOnGivenFilters(InvestmentFilterRequest filterRequest) {
        if (filterRequest == null) {
            return new Error(Response.Status.BAD_REQUEST, new BadRequestException("Investment Filter request is empty"), null);
        }
        LocalDate localStartDate = filterRequest.getFromDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getFromDate()) : null;
        LocalDate localEndDate = filterRequest.getToDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getToDate()) : null;
        List<EquityItem> results = investmentRepository.getEquityItemsBasedOnGivenFilters(filterRequest.getEquityId(), localStartDate, localEndDate, filterRequest.getInvestmentCategories(), filterRequest.getTradeType(),Integer.MAX_VALUE);
        return new Success(Response.Status.OK, null, results);
    }

    @Override
    public File exportDataToExcel(InvestmentFilterRequest filterRequest) {
        LocalDate localStartDate = filterRequest.getFromDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getFromDate()) : null;
        LocalDate localEndDate = filterRequest.getToDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getToDate()) : null;
        List<EquityItem> results = investmentRepository.getEquityItemsBasedOnGivenFilters(filterRequest.getEquityId(), localStartDate, localEndDate, filterRequest.getInvestmentCategories(), filterRequest.getTradeType(),Integer.MAX_VALUE);
        List<String> investmentTypes = investmentTypeRepository.getAllInvestmentCategories();
        return createExcel(investmentTypes,results);
    }

    private File createExcel(List<String> investmentTypes,List<EquityItem> results) {
       try {
           InvestmentExcelGenerator generator = new InvestmentExcelGenerator();
           return generator.createExcel(investmentTypes,results);
       }
       catch (IOException ignored) {
           return null;
       }
    }

}

package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.*;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentTypeRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.InvestmentPdfGenerator;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.io.File;
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
    public EquityItem getById(String investmentNumber) {
        return investmentRepository.getByNumber(investmentNumber);
    }

    @Override
    public EquityItem create(EquityItem item) {
        if (
                item.getInvestmentTypeId() == null ||
                        item.getTradeDate() == null ||
                        item.getTradeType() == null ||
                        item.getQuantity() == null ||
                        item.getPrice() == null ||
                        item.getAmountInvested() == null
        ) {
            throw new BadRequestException("Invalid request");
        }

        InvestmentType investmentType = investmentTypeRepository.getById(item.getInvestmentTypeId());
        if (investmentType == null) {
            throw new BadRequestException("Invalid investment category");
        }
        LocalDateTime now = LocalDateTime.now();
        InvestmentItem investmentItem = new InvestmentItem(
                investmentType,
                item.getSymbol(),
                item.getTradeDate(),
                item.getTradeType(),
                item.getQuantity(),
                item.getPrice(),
                item.getAmountInvested(),
                item.getCreatedBy(),
                now,
                now
        );
        investmentItem = investmentRepository.create(investmentItem);
        item.setInvestmentNumber(investmentItem.getInvestmentId());
        return item;
    }

    @Override
    public NetworkResponse addEquityItems(List<EquityItem> equityItems, String userId) {
        List<InvestmentItem> investmentItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (EquityItem equityItem : equityItems) {
            InvestmentType investmentType = investmentTypeRepository.getById(equityItem.getInvestmentTypeId());
            if (investmentType != null) {
                InvestmentItem investmentItem = new InvestmentItem(
                        investmentType,
                        equityItem.getSymbol(),
                        equityItem.getTradeDate(),
                        equityItem.getTradeType().toUpperCase(),
                        equityItem.getQuantity(),
                        equityItem.getPrice(),
                        equityItem.getAmountInvested(),
                        userId,
                        now,
                        now
                );
                investmentItems.add(investmentItem);
            }
        }
        if (!investmentItems.isEmpty()) {
            List<InvestmentItem> investmentItemList = investmentRepository.bulkCreate(investmentItems);
            if (investmentItemList != null && !investmentItemList.isEmpty()) {
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
    public EquityItem update(EquityItem item) {
        if (
                item.getInvestmentNumber() == null ||
                        item.getInvestmentTypeId() == null ||
                        item.getTradeDate() == null ||
                        item.getTradeType() == null ||
                        item.getQuantity() == null ||
                        item.getPrice() == null ||
                        item.getAmountInvested() == null
        ) {
            throw new BadRequestException("Invalid request");
        }
        InvestmentItem investmentItem = investmentRepository.getById(item.getInvestmentNumber());
        if (investmentItem == null) {
            throw new BadRequestException("Investment doesn't exist");
        }
        InvestmentType investmentType = investmentTypeRepository.getById(item.getInvestmentTypeId());
        if (investmentType == null) {
            throw new BadRequestException("Investment category doesn't exist");
        }
        investmentItem.setAmountInvested(item.getAmountInvested());
        investmentItem.setInvestmentType(investmentType);
        investmentItem.setPrice(item.getPrice());
        investmentItem.setSymbol(item.getSymbol());
        investmentItem.setUnits(item.getQuantity());
        investmentItem.setAmountInvested(item.getAmountInvested());
        investmentItem.setTradeDate(item.getTradeDate());
        investmentItem.setTradeType(item.getTradeType());
        investmentItem.setUpdatedOn(LocalDateTime.now());
        investmentRepository.update(investmentItem);
        return item;
    }


    @Override
    public EquityItem delete(String equityNumber) {
        InvestmentItem item = investmentRepository.getById(equityNumber);
        if (item == null) {
            throw new BadRequestException("Investment Item doesn't exist");
        }
        investmentRepository.delete(item);
        return new EquityItem(
                item.getInvestmentId(),
                item.getInvestmentType().getInvestmentTypeId(),
                item.getInvestmentType().getInvestmentName(),
                item.getSymbol(),
                item.getTradeDate(),
                item.getTradeType(),
                item.getUnits(),
                item.getPrice(),
                item.getAmountInvested(),
                null,
                null,
                null
        );
    }

    @Override
    public NetworkResponse getInvestmentOverview(String startDate, String endDate, String userId) {
        LocalDate localStartDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate localEndDate = DateUtils.fromStringToLocalDate(endDate);

        List<InvestmentTypeOverview> overviews = investmentRepository.getTotalInvestmentItemsByTypeGivenDateRange(localStartDate, localEndDate, userId);
        AtomicReference<Double> totalInvestmentAmount = new AtomicReference<>(0.0);
        overviews.forEach(item -> totalInvestmentAmount.updateAndGet(v -> v + item.getTotalBuyAmount()));
        InvestmentOverview investmentOverview = new InvestmentOverview(totalInvestmentAmount.get(), overviews);
        return new Success(Response.Status.OK, null, investmentOverview);

    }

    @Override
    public NetworkResponse getInvestmentItemsBasedOnGivenFilters(InvestmentFilterRequest filterRequest, String userId) {
        if (filterRequest == null) {
            return new Error(Response.Status.BAD_REQUEST, new BadRequestException("Investment Filter request is empty"), null);
        }
        LocalDate localStartDate = filterRequest.getFromDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getFromDate()) : null;
        LocalDate localEndDate = filterRequest.getToDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getToDate()) : null;
        List<EquityItem> results = investmentRepository.getEquityItemsBasedOnGivenFilters(
                filterRequest.getEquityId(),
                localStartDate,
                localEndDate,
                userId,
                filterRequest.getInvestmentCategories(),
                filterRequest.getTradeType(),
                filterRequest.getPageNo(),
                filterRequest.getLimit()
        );
        return new Success(Response.Status.OK, null, results);
    }

    @Override
    public File exportDataToExcel(InvestmentFilterRequest filterRequest, String userId) {
        LocalDate localStartDate = filterRequest.getFromDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getFromDate()) : null;
        LocalDate localEndDate = filterRequest.getToDate() != null ? DateUtils.fromStringToLocalDate(filterRequest.getToDate()) : null;
        List<EquityItem> results = investmentRepository.getEquityItemsBasedOnGivenFilters(
                filterRequest.getEquityId(),
                localStartDate,
                localEndDate,
                userId,
                filterRequest.getInvestmentCategories(),
                filterRequest.getTradeType(),
                0, 0
        );
        InvestmentPdfGenerator generator = new InvestmentPdfGenerator();
        return generator.createPdf(localStartDate,localEndDate,results);
    }

    @Override
    public InvestmentPortfolio getInvestmentPortfolio(String userId) {
        List<InvestmentPortfolioItem> portfolioItems = investmentRepository.getInvestmentPortfolio(userId);
        Double totalInvestment = 0d;
        for (InvestmentPortfolioItem item : portfolioItems) {
            totalInvestment+=item.getAssetAllocated();
        }

        Double finalTotalInvestment = totalInvestment;
        portfolioItems = portfolioItems.stream().map(investmentPortfolioItem -> {
            investmentPortfolioItem.setAllocationPercentage((investmentPortfolioItem.getAssetAllocated()/ finalTotalInvestment)*100f);
            return investmentPortfolioItem;
        }).toList();
        return new InvestmentPortfolio(totalInvestment,portfolioItems);
    }



}

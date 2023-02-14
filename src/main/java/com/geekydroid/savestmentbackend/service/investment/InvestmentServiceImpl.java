package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentTypeRepository;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentServiceImpl implements InvestmentService {

    @Inject
    InvestmentRepository investmentRepository;

    @Inject
    InvestmentTypeRepository investmentTypeRepository;

    @Override
    public NetworkResponse addEquityItems(List<EquityItem> equityItems) {
        System.out.println(equityItems.toString());
        List<InvestmentItem> investmentItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (EquityItem equityItem : equityItems) {
            InvestmentType investmentType = investmentTypeRepository.findInvestmentTypeByName(equityItem.getInvestmentType());
            if (investmentType != null) {
                InvestmentItem investmentItem = new InvestmentItem(
                        investmentType,
                        equityItem.getSymbol(),
                        equityItem.getTradeDate(),
                        equityItem.getTradeType(),
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
        if (investmentItems.size() > 0) {
            List<InvestmentItem> investmentItemList = investmentRepository.addEquity(investmentItems);
            if (investmentItemList != null && investmentItemList.size() > 0) {
                return new Success(Response.Status.CREATED, null, investmentItemList);
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
        }
        else {
            InvestmentItem item = investmentRepository.findInvestmentItemById(EquityNumber);
            if (item == null) {
                errorResp.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                errorResp.setMessage("Equity with number "+EquityNumber+" doesn't exist.");
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
        return new Success(Response.Status.OK, null, item);
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


}
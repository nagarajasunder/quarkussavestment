package com.geekydroid.savestmentbackend.domain.investment;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class EquityItem {


    private String investmentType;
    private String symbol;


    private LocalDate tradeDate;


    private TradeType tradeType;

    private Double quantity;

    private Double price;


    private Double amountInvested;


    private UUID createdBy;


    private LocalDateTime createdOn;


    private LocalDateTime updatedOn;

    public EquityItem(
            String investmentType,
            String symbol,
            LocalDate tradeDate,
            TradeType tradeType,
            Double quantity,
            Double price,
            Double amountInvested,
            UUID createdBy,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {

        this.investmentType = investmentType;
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.amountInvested = amountInvested;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public EquityItem() {
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }


}

package com.geekydroid.savestmentbackend.domain.investment;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EquityItem {

    @SerializedName("investment_type")
    private String investmentType;
    @SerializedName("symbol")
    private String symbol;

    @SerializedName("trade_date")
    private LocalDate tradeDate;

    @SerializedName("trade_type")
    private TradeType tradeType;

    @SerializedName("units")
    private Double quantity;

    @SerializedName("price")
    private Double price;

    @SerializedName("amount_invested")
    private Double amountInvested;

    @SerializedName("created_on")
    private LocalDateTime createdOn;

    @SerializedName("updated_on")
    private LocalDateTime updatedOn;

    public EquityItem(
            String symbol,
            LocalDate tradeDate,
            TradeType tradeType,
            Double quantity,
            Double price,
            Double amountInvested,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.amountInvested = amountInvested;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public EquityItem() {
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

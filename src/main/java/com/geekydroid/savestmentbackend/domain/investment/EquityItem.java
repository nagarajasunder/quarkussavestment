package com.geekydroid.savestmentbackend.domain.investment;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class EquityItem {

    @JsonbProperty("investment_type")
    private String investmentType;

    @JsonbProperty("symbol")
    private String symbol;

    @JsonbProperty("trade_date")
    private LocalDate tradeDate;

    @JsonbProperty("trade_type")

    private String tradeType;

    @JsonbProperty("quantity")
    private Double quantity;

    @JsonbProperty("price")
    private Double price;

    @JsonbProperty("amount_invested")
    private Double amountInvested;

    @JsonbProperty("created_by")
    private UUID createdBy;

    @JsonbProperty("created_on")
    private String createdOn;

    @JsonbProperty("updated_on")
    private String updatedOn;

    public EquityItem(
            String investmentType,
            String symbol,
            LocalDate tradeDate,
            String tradeType,
            Double quantity,
            Double price,
            Double amountInvested,
            UUID createdBy,
            String createdOn,
            String updatedOn
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "EquityItem{" +
                "investmentType='" + investmentType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", tradeDate=" + tradeDate +
                ", tradeType=" + tradeType +
                ", quantity=" + quantity +
                ", price=" + price +
                ", amountInvested=" + amountInvested +
                ", createdBy=" + createdBy +
                '}';
    }
}

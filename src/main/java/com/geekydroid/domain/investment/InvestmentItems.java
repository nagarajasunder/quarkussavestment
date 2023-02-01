package com.geekydroid.domain.investment;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "INVESTMENT_ITEMS")
public class InvestmentItems {

    @SequenceGenerator(
            name = "investment_item_id_generator",
            sequenceName = "investment_item_id_generator",
            initialValue = 10,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            generator = "investment_item_id_generator",
            strategy = GenerationType.IDENTITY
    )
    private Long investmentId;

    @OneToOne
    @JoinColumn(name = "investment_types_investment_type_id")
    private InvestmentTypes investmentTypes;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "TRADE_DATE")
    private String tradeDate;

    @Column(name = "TRADE_TYPE")
    private TradeType tradeType;

    @Column(name = "UNITS")
    private Double units;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "AMOUNT_INVESTED")
    private Double amountInvested;

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "CREATED_ON")
    private LocalDate createdOn;


    @Column(name = "UPDATED_ON")
    private LocalDate updatedOn;

    public InvestmentItems(InvestmentTypes investmentTypes, String symbol, String tradeDate, TradeType tradeType, Double units, Double price, Double amountInvested, UUID createdBy, LocalDate createdOn, LocalDate updatedOn) {
        this.investmentTypes = investmentTypes;
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.units = units;
        this.price = price;
        this.amountInvested = amountInvested;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public InvestmentItems() {
    }

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Double getUnits() {
        return units;
    }

    public void setUnits(Double units) {
        this.units = units;
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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public InvestmentTypes getInvestmentTypes() {
        return investmentTypes;
    }

    public void setInvestmentTypes(InvestmentTypes investmentTypes) {
        this.investmentTypes = investmentTypes;
    }
}

enum TradeType {
    BUY,
    SELL
}
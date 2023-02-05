package com.geekydroid.savestmentbackend.domain.investment;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "INVESTMENT_ITEMS")
public class InvestmentItem {

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
    private LocalDate tradeDate;

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
    private LocalDateTime createdOn;


    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    public InvestmentItem(InvestmentTypes investmentTypes, String symbol, LocalDate tradeDate, TradeType tradeType, Double units, Double price, Double amountInvested, UUID createdBy, LocalDateTime createdOn, LocalDateTime updatedOn) {
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

    public InvestmentItem() {
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

    public InvestmentTypes getInvestmentTypes() {
        return investmentTypes;
    }

    public void setInvestmentTypes(InvestmentTypes investmentTypes) {
        this.investmentTypes = investmentTypes;
    }
}


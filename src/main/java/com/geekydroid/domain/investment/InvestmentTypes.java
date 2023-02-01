package com.geekydroid.domain.investment;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "INVESTMENT_TYPES")
@Entity
public class InvestmentTypes {

    public InvestmentTypes() {
    }

    public InvestmentTypes(Long investmentTypeId, String investmentName, LocalDate createdOn, LocalDate updatedOn) {
        this.investmentTypeId = investmentTypeId;
        this.investmentName = investmentName;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    @SequenceGenerator(
            name = "investment_id_generator",
            sequenceName = "investment_id_generator",
            initialValue = 10,
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "investment_id_generator",
            strategy = GenerationType.IDENTITY
    )
    @Id
    @Column(name = "INVESTMENT_TYPE_ID")
    private Long investmentTypeId;


    @Column(name = "INVESTMENT_NAME")
    private String investmentName;

    @Column(name = "CREATED_ON")
    private LocalDate createdOn;

    @Column(name = "UPDATED_ON")
    private LocalDate updatedOn;


    public Long getInvestmentTypeId() {
        return investmentTypeId;
    }

    public void setInvestmentTypeId(Long investmentTypeId) {
        this.investmentTypeId = investmentTypeId;
    }

    public String getInvestmentName() {
        return investmentName;
    }

    public void setInvestmentName(String investmentName) {
        this.investmentName = investmentName;
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
}

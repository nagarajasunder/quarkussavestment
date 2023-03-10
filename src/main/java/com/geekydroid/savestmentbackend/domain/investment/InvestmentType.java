package com.geekydroid.savestmentbackend.domain.investment;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "INVESTMENT_TYPES")
@Entity
public class InvestmentType extends PanacheEntityBase {

    public InvestmentType() {
    }

    public InvestmentType(Long investmentTypeId, String investmentName, LocalDateTime createdOn, LocalDateTime updatedOn) {
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
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;


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

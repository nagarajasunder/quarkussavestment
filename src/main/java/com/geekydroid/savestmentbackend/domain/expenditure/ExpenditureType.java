package com.geekydroid.savestmentbackend.domain.expenditure;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXPENDITURE_TYPE")
public class ExpenditureType {

    @SequenceGenerator(
            name = "expenditure_type_id_generator",
            sequenceName = "expenditure_type_id_generator",
            initialValue = 10,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            generator = "expenditure_type_id_generator",
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "expenditure_type_id")
    private Long expenditureTypeId;

    @Column(name = "expenditure_name")
    private String expenditureName;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public ExpenditureType(String expenditureName, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.expenditureName = expenditureName;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public ExpenditureType() {
    }

    public Long getExpenditureTypeId() {
        return expenditureTypeId;
    }

    public void setExpenditureTypeId(Long expenditureTypeId) {
        this.expenditureTypeId = expenditureTypeId;
    }

    public String getExpenditureName() {
        return expenditureName;
    }

    public void setExpenditureName(String expenditureName) {
        this.expenditureName = expenditureName;
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

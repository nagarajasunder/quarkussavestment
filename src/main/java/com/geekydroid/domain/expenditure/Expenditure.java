package com.geekydroid.domain.expenditure;

import com.geekydroid.domain.enums.Paymode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "EXPENDITURE")
public class Expenditure {

    @SequenceGenerator(
            name = "expenditure_id_generator",
            sequenceName = "expenditure_id_generator",
            initialValue = 10,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            generator = "expenditure_id_generator",
            strategy = GenerationType.IDENTITY
    )
    private Long expenditureId;

    @Transient
    private String expenditureNumber;

    @ManyToOne
    @JoinColumn(name = "expenditure_category_expenditure_category_id")
    private ExpenditureCategory expenditureCategory;

    @Column(name = "expenditure_amount")
    private Double expenditureAmount;

    @Column(name = "expenditure_description")
    private String expenditureDescription;

    @Column(name = "mode_of_payment")
    private Paymode paymode;

    @Column(name = "date_of_expenditure")
    private LocalDateTime expenditureDate;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public Expenditure
            (
                    String expenditureNumber,
                    ExpenditureCategory expenditureCategory,
                    Double expenditureAmount,
                    String expenditureDescription,
                    Paymode paymode,
                    LocalDateTime expenditureDate,
                    UUID createdBy,
                    LocalDateTime createdOn,
                    LocalDateTime updatedOn
            ) {
        this.expenditureNumber = expenditureNumber;
        this.expenditureCategory = expenditureCategory;
        this.expenditureAmount = expenditureAmount;
        this.expenditureDescription = expenditureDescription;
        this.paymode = paymode;
        this.expenditureDate = expenditureDate;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Expenditure() {
    }

    public Long getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(Long expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getExpenditureNumber() {
        return expenditureNumber;
    }

    public void setExpenditureNumber(String expenditureNumber) {
        this.expenditureNumber = expenditureNumber;
    }

    public Double getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(Double expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }

    public String getExpenditureDescription() {
        return expenditureDescription;
    }

    public void setExpenditureDescription(String expenditureDescription) {
        this.expenditureDescription = expenditureDescription;
    }

    public Paymode getPaymode() {
        return paymode;
    }

    public void setPaymode(Paymode paymode) {
        this.paymode = paymode;
    }

    public LocalDateTime getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(LocalDateTime expenditureDate) {
        this.expenditureDate = expenditureDate;
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

    public ExpenditureCategory getExpenditureCategory() {
        return expenditureCategory;
    }

    public void setExpenditureCategory(ExpenditureCategory expenditureCategory) {
        this.expenditureCategory = expenditureCategory;
    }
}

package com.geekydroid.savestmentbackend.domain.expenditure;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXPENDITURE_CATEGORY")
public class ExpenditureCategory extends PanacheEntityBase {

    @SequenceGenerator(
            name = "expenditure_category_id_generator",
            sequenceName = "expenditure_category_id_generator",
            initialValue = 10,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            generator = "expenditure_category_id_generator",
            strategy = GenerationType.IDENTITY
    )
    private Long expenditureCategoryId;

    @OneToOne
    @JoinColumn(name = "expenditure_type_expenditure_type_id")
    private ExpenditureType expenditureType;


    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public ExpenditureCategory(
            ExpenditureType expenditureType,
            String categoryName,
            String createdBy,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.expenditureType = expenditureType;
        this.categoryName = categoryName;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public ExpenditureCategory() {
    }

    public Long getExpenditureCategoryId() {
        return expenditureCategoryId;
    }

    public void setExpenditureCategoryId(Long expenditureCategoryId) {
        this.expenditureCategoryId = expenditureCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
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

    public ExpenditureType getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(ExpenditureType expenditureType) {
        this.expenditureType = expenditureType;
    }

}

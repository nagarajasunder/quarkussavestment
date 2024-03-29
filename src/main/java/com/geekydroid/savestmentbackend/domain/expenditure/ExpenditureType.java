package com.geekydroid.savestmentbackend.domain.expenditure;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "EXPENDITURE_TYPE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenditureType extends PanacheEntityBase {

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

    @OneToMany(mappedBy = "expenditureType")
    private List<ExpenditureCategory> expenditureCategories;

    public ExpenditureType(String expenditureName, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.expenditureName = expenditureName;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
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

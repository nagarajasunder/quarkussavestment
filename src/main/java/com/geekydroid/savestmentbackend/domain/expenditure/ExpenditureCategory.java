package com.geekydroid.savestmentbackend.domain.expenditure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EXPENDITURE_CATEGORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenditureCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expenditure_category_id")
    private Long expenditureCategoryId;

    @ManyToOne
    @JoinColumn(name = "expenditure_type_expenditure_type_id")
    private ExpenditureType expenditureType;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "is_common", columnDefinition = "boolean default false")
    private boolean isCommon;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "expenditureCategory")
    private List<Expenditure> expenditures;

    public void addExpenditure(Expenditure expenditure) {
        expenditures.add(expenditure);
    }

    public ExpenditureCategory(
            ExpenditureType expenditureType,
            String categoryName,
            boolean isCommon,
            String createdBy,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.expenditureType = expenditureType;
        this.expenditureType.getExpenditureCategories().add(this);
        this.categoryName = categoryName;
        this.isCommon = isCommon;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.expenditures = new ArrayList<>();
    }
}

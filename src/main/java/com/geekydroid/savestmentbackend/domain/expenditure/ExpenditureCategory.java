package com.geekydroid.savestmentbackend.domain.expenditure;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "EXPENDITURE_CATEGORY")
@NamedQueries(
        {
                @NamedQuery(
                        name = "ExpenditureCategory.deleteByName",
                        query = "delete from ExpenditureCategory E where E.categoryName in :categoryName and isCommon != true"
                )
        }
)
@Getter
@Setter
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
    @Column(name = "expenditure_category_id")
    private Long expenditureCategoryId;

    @OneToOne
    @JoinColumn(name = "expenditure_type_expenditure_type_id")
    private ExpenditureType expenditureType;

    @OneToMany(mappedBy = "expenditureCategory")
    private List<Expenditure> expenditures;


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

    public ExpenditureCategory(
            ExpenditureType expenditureType,
            String categoryName,
            boolean isCommon,
            String createdBy,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.expenditureType = expenditureType;
        this.categoryName = categoryName;
        this.isCommon = isCommon;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public ExpenditureCategory() {
    }

    public static void deleteExpenditureCategoryByName(List<String> categoryName) {
      delete("#ExpenditureCategory.deleteByName", Parameters.with("categoryName",categoryName));

    }

}

package com.geekydroid.savestmentbackend.domain.expenditure;

import com.geekydroid.savestmentbackend.db.ExpenditureNumberSequenceGenerator;
import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXPENDITURE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expenditure {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "expenditure_number_generator"
    )
    @GenericGenerator(
            name = "expenditure_number_generator",
            strategy = "com.geekydroid.savestmentbackend.db.ExpenditureNumberSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "EXP_"),
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%d"),
            }
    )
    @Column(name = "expenditure_number")
    private String expenditureNumber;

    @ManyToOne
    @JoinColumn(name = "expenditure_category_expenditure_category_id")
    private ExpenditureCategory expenditureCategory;

    @Column(name = "expenditure_amount",columnDefinition = "NUMERIC")
    private Double expenditureAmount;

    @Column(name = "expenditure_description")
    private String expenditureDescription;

    @Column(name = "mode_of_payment")
    private Paymode paymode;

    @Column(name = "date_of_expenditure")
    private LocalDate expenditureDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public Expenditure
            (
                    ExpenditureCategory expenditureCategory
            ) {
        expenditureCategory.addExpenditure(this);
        this.expenditureCategory = expenditureCategory;
    }
}

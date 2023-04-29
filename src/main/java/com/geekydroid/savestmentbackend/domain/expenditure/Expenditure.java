package com.geekydroid.savestmentbackend.domain.expenditure;

import com.geekydroid.savestmentbackend.db.ExpenditureNumberSequenceGenerator;
import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "EXPENDITURE")
@NamedQueries(
        {

                @NamedQuery(
                        name = "Expenditure.deleteByExpNumber",
                        query = "delete from Expenditure e where e.expenditureNumber in ?1"
                )
        }
)
@Getter
@Setter
public class Expenditure extends PanacheEntityBase {
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

    @Column(name = "expenditure_amount")
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
                    ExpenditureCategory expenditureCategory,
                    Double expenditureAmount,
                    String expenditureDescription,
                    Paymode paymode,
                    LocalDate expenditureDate,
                    String createdBy,
                    LocalDateTime createdOn,
                    LocalDateTime updatedOn
            ) {
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

    public static void deleteExpenditureByExpNumber(List<String> expNumber) {
        delete("#Expenditure.deleteByExpNumber", expNumber);
    }

    @Override
    public String toString() {
        return "Expenditure{" +
                "expenditureNumber='" + expenditureNumber + '\'' +
                ", expenditureCategory=" + expenditureCategory +
                ", expenditureAmount=" + expenditureAmount +
                ", expenditureDescription='" + expenditureDescription + '\'' +
                ", paymode=" + paymode +
                ", expenditureDate=" + expenditureDate +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}

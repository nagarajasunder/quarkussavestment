package com.geekydroid.savestmentbackend.domain.investment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "INVESTMENT_TYPES")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVESTMENT_TYPE_ID")
    private Long investmentTypeId;


    @Column(name = "INVESTMENT_NAME")
    private String investmentName;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "investmentType")
    private List<InvestmentItem> investments;

    @Column(name= "IS_COMMON")
    private boolean isCommon;

    public InvestmentType(
            String investmentName,
            Boolean isCommon,
            String createdBy,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.investmentName = investmentName;
        this.isCommon = isCommon;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.investments = new ArrayList<>();
    }

}

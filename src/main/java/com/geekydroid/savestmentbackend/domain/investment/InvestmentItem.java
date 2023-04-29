package com.geekydroid.savestmentbackend.domain.investment;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import com.geekydroid.savestmentbackend.db.EquityNumberSequenceGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "INVESTMENT_ITEMS")
@Getter
@Setter
public class InvestmentItem extends PanacheEntityBase {

    @GenericGenerator(
            name = "investment_number_seq",
            strategy = "com.geekydroid.savestmentbackend.db.EquityNumberSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = EquityNumberSequenceGenerator.VALUE_PREFIX_PARAMETER,
                            value = "INV_"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = EquityNumberSequenceGenerator.NUMBER_FORMAT_PARAMETER,
                            value = "%d"
                    )
            }
    )
    @Id
    @GeneratedValue(
            generator = "investment_number_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "INVESTMENT_ID")
    private String investmentId;

    @OneToOne
    @JoinColumn(name = "investment_types_investment_type_id")
    private InvestmentType investmentType;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "TRADE_DATE")
    private LocalDate tradeDate;

    @Column(name = "TRADE_TYPE")
    private String tradeType;

    @Column(name = "UNITS")
    private Double units;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "AMOUNT_INVESTED")
    private Double amountInvested;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;


    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    public InvestmentItem(InvestmentType investmentType, String symbol, LocalDate tradeDate, String tradeType, Double units, Double price, Double amountInvested, String createdBy, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.investmentType = investmentType;
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.units = units;
        this.price = price;
        this.amountInvested = amountInvested;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public InvestmentItem() {
    }

}


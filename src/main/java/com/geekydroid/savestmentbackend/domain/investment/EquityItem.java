package com.geekydroid.savestmentbackend.domain.investment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class EquityItem {

    private String investmentNumber;
    private String investmentType;

    private String symbol;

    private LocalDate tradeDate;

    private String tradeType;

    private Double quantity;

    private Double price;
    private Double amountInvested;

    private UUID createdBy;

    private String createdOn;

    private String updatedOn;


    @Override
    public String toString() {
        return "EquityItem{" +
                "investmentType='" + investmentType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", tradeDate=" + tradeDate +
                ", tradeType=" + tradeType +
                ", quantity=" + quantity +
                ", price=" + price +
                ", amountInvested=" + amountInvested +
                ", createdBy=" + createdBy +
                '}';
    }
}

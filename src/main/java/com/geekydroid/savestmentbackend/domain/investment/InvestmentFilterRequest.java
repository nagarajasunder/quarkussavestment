package com.geekydroid.savestmentbackend.domain.investment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
public class InvestmentFilterRequest {

    private String equityId;
    private String fromDate;
    private String toDate;
    private List<Long> investmentCategories;
    private String tradeType;
    private int pageNo;
    private int limit;


}

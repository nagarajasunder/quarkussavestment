package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentTypeOverview;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_ITEMS;
import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_TYPES;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.when;

@ApplicationScoped
@Transactional
public class InvestmentRepositoryImpl implements InvestmentRepository {

    @Inject
    DSLContext context;

    @Override
    public List<InvestmentItem> addEquity(List<InvestmentItem> investmentItems) {
        InvestmentItem.persist(investmentItems);
        return investmentItems;
    }

    @Override
    public InvestmentItem updateEquity(String equityNumber, EquityItem equityItem, InvestmentType investmentType) {
        InvestmentItem entity = InvestmentItem.findById(equityNumber);
        if (entity == null) {
            throw new NotFoundException("Equity with equity number " + equityNumber + " does not exist");
        }
        LocalDateTime now = LocalDateTime.now();
        entity.setInvestmentTypes(investmentType);
        entity.setSymbol(equityItem.getSymbol());
        entity.setUnits(equityItem.getQuantity());
        entity.setPrice(equityItem.getPrice());
        entity.setAmountInvested(equityItem.getAmountInvested());
        entity.setUpdatedOn(now);
        entity.setTradeType(equityItem.getTradeType());
        entity.setTradeDate(equityItem.getTradeDate());
        return entity;

    }

    @Override
    public void deleteEquity(String investmentNumber) {
        InvestmentItem entity = InvestmentItem.findById(investmentNumber);
        if (entity == null) {
            throw new NotFoundException("Equity with equity number " + investmentNumber + " does not exist");
        }
        entity.delete();
    }

    @Override
    public List<InvestmentItem> getAllEquityItems() {
        return InvestmentItem.listAll();
    }

    @Override
    public InvestmentItem findInvestmentItemById(String investmentNumber) {
        return InvestmentItem.findById(investmentNumber);
    }

    @Override
    public List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(LocalDate startDate, LocalDate endDate) {

        Condition dateFilter = INVESTMENT_ITEMS.TRADE_DATE.between(startDate, endDate);

        List<InvestmentTypeOverview> result = context
                .select(
                        INVESTMENT_TYPES.INVESTMENT_NAME.as("investment_name"),
                        sum(
                                when(
                                        dateFilter.and(INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.BUY.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED
                                ).otherwise(BigDecimal.ZERO)
                        ).as("total_buy_value"),
                        sum(
                                when(dateFilter
                                                .and(INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.SELL.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED).otherwise(BigDecimal.ZERO)
                        ).as("total_sell_value")
                )
                .from(INVESTMENT_TYPES)
                .leftJoin(INVESTMENT_ITEMS)
                .on(INVESTMENT_TYPES.INVESTMENT_TYPE_ID.eq(INVESTMENT_ITEMS.INVESTMENT_TYPES_INVESTMENT_TYPE_ID))
                .groupBy(INVESTMENT_TYPES.INVESTMENT_NAME)
                .fetchInto(InvestmentTypeOverview.class);

        return result;
    }

    @Override
    public List<EquityItem> getEquityItemsGivenDateRange(LocalDate localStartDate, LocalDate localEndDate) {

        return context.select(
                        INVESTMENT_TYPES.INVESTMENT_NAME.as("investment_type"),
                        INVESTMENT_ITEMS.SYMBOL.as("symbol"),
                        INVESTMENT_ITEMS.TRADE_DATE.as("trade_date"),
                        INVESTMENT_ITEMS.TRADE_TYPE.as("trade_type"),
                        INVESTMENT_ITEMS.UNITS.as("quantity"),
                        INVESTMENT_ITEMS.PRICE,
                        INVESTMENT_ITEMS.AMOUNT_INVESTED,
                        INVESTMENT_ITEMS.CREATED_BY,
                        INVESTMENT_ITEMS.CREATED_ON.cast(SQLDataType.VARCHAR),
                        INVESTMENT_ITEMS.UPDATED_ON.cast(SQLDataType.VARCHAR)
                )
                .from(INVESTMENT_ITEMS)
                .leftJoin(INVESTMENT_TYPES)
                .on(INVESTMENT_ITEMS.INVESTMENT_TYPES_INVESTMENT_TYPE_ID.eq(INVESTMENT_TYPES.INVESTMENT_TYPE_ID))
                .where(INVESTMENT_ITEMS.TRADE_DATE.between(localStartDate, localEndDate))
                .fetchInto(EquityItem.class);


    }


}

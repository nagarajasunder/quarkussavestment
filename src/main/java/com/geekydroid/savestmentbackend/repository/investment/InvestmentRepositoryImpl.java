package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import com.geekydroid.savestmentbackend.domain.investment.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record11;
import org.jooq.SelectConditionStep;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.geekydroid.savestment.domain.db.Tables.*;
import static org.jooq.impl.DSL.*;

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
        entity.setInvestmentType(investmentType);
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
    public List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(LocalDate startDate, LocalDate endDate,String userId) {

        Condition dateFilter = INVESTMENT_ITEMS.CREATED_BY.eq(userId).and(INVESTMENT_ITEMS.TRADE_DATE.between(startDate, endDate));

        List<InvestmentTypeOverview> result = context
                .select(
                        INVESTMENT_TYPES.INVESTMENT_NAME,
                        sum(
                                when(
                                        dateFilter.and(INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.BUY.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED
                                ).otherwise(BigDecimal.ZERO)
                        ),
                        sum(
                                when(dateFilter
                                                .and(INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.SELL.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED).otherwise(BigDecimal.ZERO)
                        )
                )
                .from(INVESTMENT_TYPES)
                .leftJoin(INVESTMENT_ITEMS)
                .on(INVESTMENT_TYPES.INVESTMENT_TYPE_ID.eq(INVESTMENT_ITEMS.INVESTMENT_TYPES_INVESTMENT_TYPE_ID))
                .groupBy(INVESTMENT_TYPES.INVESTMENT_NAME)
                .fetchInto(InvestmentTypeOverview.class);
        return result;
    }


    @Override
    public List<EquityItem> getEquityItemsBasedOnGivenFilters(
            String equityId,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<String> investmentCategories,
            String tradeType,
            int limit
    ) {
        Condition condition = chainFilters(equityId, fromDate, toDate, userId,investmentCategories, tradeType);

        SelectConditionStep<Record11<String, String, String, LocalDate, String, BigDecimal, BigDecimal, BigDecimal, String, String, String>> selectedQuery = context.select(
                        INVESTMENT_ITEMS.INVESTMENT_ID.as("investment_number"),
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
                .where(condition);

        if (limit != Integer.MAX_VALUE) {
            return selectedQuery
                    .orderBy(INVESTMENT_ITEMS.INVESTMENT_ID.desc())
                    .limit(limit)
                    .fetchInto(EquityItem.class);
        } else {
            return selectedQuery
                    .fetchInto(EquityItem.class);
        }
    }


    private Condition chainFilters(
            String equityId,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<String> investmentCategories,
            String tradeType
    ) {
        Condition condition = noCondition();

        if (userId != null && !userId.isEmpty()) {
            condition = condition.and(INVESTMENT_ITEMS.CREATED_BY.eq(userId));
        }

        if (equityId != null && !equityId.isEmpty()) {
            condition = condition.and(INVESTMENT_ITEMS.INVESTMENT_ID.eq(equityId));
            return condition;
        }

        if (fromDate != null) {
            condition = condition.and(INVESTMENT_ITEMS.TRADE_DATE.greaterOrEqual(fromDate));
        }
        if (toDate != null) {
            condition = condition.and(INVESTMENT_ITEMS.TRADE_DATE.lessOrEqual(toDate));
        }
        if (investmentCategories != null && !investmentCategories.isEmpty()) {
            condition = condition.and(INVESTMENT_TYPES.INVESTMENT_NAME.in(investmentCategories));
        }
        if (tradeType != null && !tradeType.isEmpty() && !tradeType.equalsIgnoreCase(TradeType.UNSPECIFIED.name())) {
            condition = condition.and(INVESTMENT_ITEMS.TRADE_TYPE.eq(tradeType));
        }

        return condition;

    }


}

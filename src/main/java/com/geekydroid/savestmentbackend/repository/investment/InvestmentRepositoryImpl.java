package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentTypeOverview;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record12;
import org.jooq.SelectConditionStep;
import org.jooq.impl.SQLDataType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_ITEMS;
import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_TYPES;
import static org.jooq.impl.DSL.*;

@ApplicationScoped
@Transactional
public class InvestmentRepositoryImpl implements InvestmentRepository {

    @Inject
    DSLContext context;

    @Inject
    EntityManager entityManager;

    @Override
    public List<InvestmentItem> bulkCreate(List<InvestmentItem> investmentItems) {
        InvestmentItem.persist(investmentItems);
        return investmentItems;
    }

    @Override
    public InvestmentItem create(InvestmentItem item) {
        entityManager.persist(item);
        return item;
    }

    @Override
    public InvestmentItem getById(String id) {
        return entityManager.createQuery("select i from InvestmentItem  i where i.investmentId=?1", InvestmentItem.class)
                .setParameter(1, id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }


    @Override
    public InvestmentItem update(InvestmentItem item) {
        entityManager.merge(item);
        return item;
    }

    @Override
    public InvestmentItem delete(InvestmentItem item) {
        entityManager.remove(item);
        return item;
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
    public List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(LocalDate startDate, LocalDate endDate, String userId) {

        Condition dateFilter = INVESTMENT_ITEMS.CREATED_BY.eq(userId).and(INVESTMENT_ITEMS.TRADE_DATE.between(startDate, endDate));

        List<InvestmentTypeOverview> result = context
                .select(
                        INVESTMENT_TYPES.INVESTMENT_NAME,
                        sum(
                                when(
                                        (INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.BUY.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED
                                ).otherwise(BigDecimal.ZERO)
                        ),
                        sum(
                                when((INVESTMENT_ITEMS.TRADE_TYPE.eq(TradeType.SELL.name())),
                                        INVESTMENT_ITEMS.AMOUNT_INVESTED).otherwise(BigDecimal.ZERO)
                        )
                )
                .from(INVESTMENT_TYPES)
                .join(INVESTMENT_ITEMS)
                .on(INVESTMENT_TYPES.INVESTMENT_TYPE_ID.eq(INVESTMENT_ITEMS.INVESTMENT_TYPES_INVESTMENT_TYPE_ID))
                .where(INVESTMENT_ITEMS.CREATED_BY.eq(userId).and(INVESTMENT_ITEMS.TRADE_DATE.between(startDate, endDate)))
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
            List<Long> investmentCategories,
            String tradeType,
            int pageNo,
            int itemsPerPage
    ) {
        Condition condition = chainFilters(equityId, fromDate, toDate, userId, investmentCategories, tradeType);

        SelectConditionStep<Record12<String, Long, String, String, LocalDate, String, BigDecimal, BigDecimal, BigDecimal, String, String, String>> selectedQuery = context.select(
                        INVESTMENT_ITEMS.INVESTMENT_ID.as("investment_number"),
                        INVESTMENT_TYPES.INVESTMENT_TYPE_ID.as("investment_type_id"),
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

        if (pageNo > 0 && itemsPerPage > 0) {
            int offset = ((pageNo - 1) * itemsPerPage);
            return selectedQuery
                    .orderBy(INVESTMENT_ITEMS.TRADE_DATE.desc(), INVESTMENT_ITEMS.INVESTMENT_ID.asc())
                    .limit(itemsPerPage)
                    .offset(offset)
                    .fetchInto(EquityItem.class);
        } else {
            return selectedQuery
                    .orderBy(INVESTMENT_ITEMS.TRADE_DATE.desc(), INVESTMENT_ITEMS.INVESTMENT_ID.asc())
                    .fetchInto(EquityItem.class);
        }
    }

    @Override
    public EquityItem getByNumber(String investmentNumber) {
        return context.select(
                        INVESTMENT_ITEMS.INVESTMENT_ID.as("investment_number"),
                        INVESTMENT_TYPES.INVESTMENT_TYPE_ID.as("investment_type_id"),
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
                ).from(INVESTMENT_ITEMS)
                .leftJoin(INVESTMENT_TYPES)
                .on(INVESTMENT_ITEMS.INVESTMENT_TYPES_INVESTMENT_TYPE_ID.eq(INVESTMENT_TYPES.INVESTMENT_TYPE_ID))
                .where(INVESTMENT_ITEMS.INVESTMENT_ID.eq(investmentNumber))
                .fetchSingle()
                .into(EquityItem.class);
    }


    private Condition chainFilters(
            String equityId,
            LocalDate fromDate,
            LocalDate toDate,
            String userId,
            List<Long> investmentCategories,
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
            condition = condition.and(INVESTMENT_TYPES.INVESTMENT_TYPE_ID.in(investmentCategories));
        }
        if (tradeType != null && !tradeType.isEmpty() && !tradeType.equalsIgnoreCase(TradeType.UNSPECIFIED.name())) {
            condition = condition.and(INVESTMENT_ITEMS.TRADE_TYPE.eq(tradeType));
        }

        return condition;

    }


}

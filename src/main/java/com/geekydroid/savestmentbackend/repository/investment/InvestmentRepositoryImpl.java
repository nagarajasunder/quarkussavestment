package com.geekydroid.savestmentbackend.repository.investment;


import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentItem;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentTypeOverview;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

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
        entity.setInvestmentTypes(investmentType);
        entity.setSymbol(equityItem.getSymbol());
        entity.setUnits(equityItem.getQuantity());
        entity.setPrice(equityItem.getPrice());
        entity.setAmountInvested(equityItem.getAmountInvested());
        entity.setUpdatedOn(equityItem.getUpdatedOn());
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
    public List<InvestmentTypeOverview> getTotalInvestmentItemsByTypeGivenDateRange(String startDate, String endDate) {

//        Table<?> totalBuySum = context.select(
//                        sum(field("INVESTMENT_ITEMS.AMOUNT_INVESTED", SQLDataType.DECIMAL)).as("totalBuy")
//                )
//                .from(DSL.table("INVESTMENT_ITEMS"))
//                .where(
//                        field("INVESTMENT_ITEMS.TRADE_TYPE", SQLDataType.INTEGER.asConvertedDataType(TradeTypeConverter.getConverter())).eq(TradeType.BUY)
//                ).asTable();
//        Table<?> totalSellSum = context.select(
//                        sum(field("INVESTMENT_ITEMS.AMOUNT_INVESTED", SQLDataType.DECIMAL)).as("totalSell")
//                )
//                .from(DSL.table("INVESTMENT_ITEMS"))
//                .where(
//                        field("INVESTMENT_ITEMS.TRADE_TYPE", SQLDataType.INTEGER.asConvertedDataType(TradeTypeConverter.getConverter())).eq(TradeType.SELL)
//                ).asTable();
//
//        Result<Record> result = context.select(totalBuySum.fields())
//                .select(totalSellSum.fields())
//                .groupBy(
//                        field("INVESTMENT_TYPE.INVESTMENT_TYPE_ID")
//                ).fetch();
//
//
//        for (Record r : result) {
//            System.out.println(r.get("totalBuy")+" Total buy");
//            System.out.println(r.get("totalSell")+" Total sell");
//        }
//

        return null;
    }


}

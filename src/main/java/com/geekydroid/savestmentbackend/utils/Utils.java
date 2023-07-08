package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;

public class Utils {

    public static TradeType convertStringToTradeType(
            String tradeTypeStr
    ) {
        if (tradeTypeStr == null || tradeTypeStr.isEmpty()) {
            return null;
        }

        if (tradeTypeStr.equalsIgnoreCase("buy")) {
            return TradeType.BUY;
        } else if (tradeTypeStr.equalsIgnoreCase("sell")){
            return TradeType.SELL;
        }
        return null;
    }

    public static String convertInvestmentTypeString(String investmentType) {
         if(investmentType == null || investmentType.isEmpty()) {
             return null;
         }
         else if (investmentType.equals("INDIAN_STOCKS")) {
             return "Indian Stocks";
         }
         else if (investmentType.equals("MUTUAL_FUNDS")) {
             return "Mutual Funds";
         }
         else if(investmentType.equals("US Stocks")) {
             return "US Stocks";
         }
         else {
             return null;
         }
    }
}

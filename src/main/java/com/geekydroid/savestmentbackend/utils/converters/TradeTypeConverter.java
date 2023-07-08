package com.geekydroid.savestmentbackend.utils.converters;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import org.jooq.Converter;
import org.jooq.Function1;

public class TradeTypeConverter {

    public static Converter<Integer,String> getConverter() {
        return Converter.of(
                Integer.class,
                String.class,
                (Function1<Integer, String>) integer -> switch (integer) {
                    case 0 -> TradeType.BUY.name();
                    case 1 -> TradeType.SELL.name();
                    default -> null;
                },
                (Function1<String, Integer>) tradeType -> switch (tradeType) {
                    case "BUY" -> 0;
                    case "SELL" -> 1;
                    default -> null;
                }
        );
    }

}

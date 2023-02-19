package com.geekydroid.savestmentbackend.utils.converters;

import com.geekydroid.savestmentbackend.domain.enums.TradeType;
import org.jooq.Converter;
import org.jooq.Function1;

public class TradeTypeConverter {

    public static Converter<Integer,TradeType> getConverter() {
        return Converter.of(
                Integer.class,
                TradeType.class,
                (Function1<Integer, TradeType>) integer -> switch (integer) {
                    case 0 -> TradeType.BUY;
                    case 1 -> TradeType.SELL;
                    default -> null;
                },
                (Function1<TradeType, Integer>) tradeType -> switch (tradeType) {
                    case BUY -> 0;
                    case SELL -> 1;
                }
        );
    }

}

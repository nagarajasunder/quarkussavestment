package com.geekydroid.savestmentbackend.utils.converters;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import org.jooq.Converter;
import org.jooq.Function1;

public class PaymodeConverters {

    public static Converter<Integer,Paymode> getConverter() {
        return Converter.of(
                Integer.class,
                Paymode.class,
                (Function1<Integer, Paymode>) integer -> switch (integer) {
                    case 0 -> Paymode.CASH;
                    case 1 -> Paymode.NEFT;
                    case 2 -> Paymode.UPI;
                    default -> null;
                },
                (Function1<Paymode, Integer>) paymode -> switch (paymode) {
                    case CASH -> 0;
                    case NEFT -> 1;
                    case UPI -> 2;
                }
        );
    }
}

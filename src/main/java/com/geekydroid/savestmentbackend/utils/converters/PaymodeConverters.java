package com.geekydroid.savestmentbackend.utils.converters;

import com.geekydroid.savestmentbackend.domain.enums.Paymode;
import org.jooq.Converter;
import org.jooq.Function1;

public class PaymodeConverters {

    public static Converter<Integer,Paymode> getConverter() {
        return Converter.of(
                Integer.class,
                Paymode.class,
                (Function1<Integer, Paymode>) integer -> {
                    if (integer == null) {
                        return null;
                    }
                    else {
                        return switch (integer) {
                            case 0 -> Paymode.CASH;
                            case 1 -> Paymode.CARD;
                            case 2 -> Paymode.UPI;
                            case 3 -> Paymode.OTHER;
                            default -> null;
                        };
                    }
                },
                (Function1<Paymode, Integer>) paymode -> switch (paymode) {
                    case CASH -> 0;
                    case CARD -> 1;
                    case UPI -> 2;
                    case OTHER -> 3;
                }
        );
    }
}

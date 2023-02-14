package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.enums.DateFormat;
import org.jooq.Converter;
import org.jooq.Function1;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {

    public static LocalDate fromStringToDate(String dateStr, DateFormat dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormat(dateFormat), Locale.ENGLISH);
        return LocalDate.parse(dateStr,formatter);
    }

    public static LocalDateTime fromStringToDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr);
    }

    public static LocalDate fromStringToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr);
    }

    private static String getDateFormat(DateFormat format) {
        switch (format) {

            case POSTGRES_FORMAT -> {
                return "yyyy-MM-dd";
            }
            default -> {
                return "";
            }
        }
    }

    public static Converter<Timestamp,String> fromSqlTimeStampToDateString(DateFormat dateFormat) {
        return Converter.of(
                Timestamp.class,
                String.class,
                timestamp -> {
                    LocalDateTime localDateTime = timestamp.toLocalDateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormat(dateFormat));
                    return localDateTime.format(formatter);
                },
                new Function1<String, Timestamp>() {
                    @Override
                    public Timestamp apply(String s) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(s,formatter);
                        return Timestamp.valueOf(dateTime);
                    }
                }
        );
    }
}



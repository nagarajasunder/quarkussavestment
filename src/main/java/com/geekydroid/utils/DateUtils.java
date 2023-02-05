package com.geekydroid.utils;

import com.geekydroid.domain.enums.DateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.geekydroid.domain.enums.DateFormat.POSTGRES_FORMAT;

public class DateUtils {

    public static LocalDate fromStringToDate(String dateStr, DateFormat dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormat(dateFormat), Locale.ENGLISH);
        return LocalDate.parse(dateStr,formatter);
    }

    public static LocalDateTime fromStringToDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr);
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
}



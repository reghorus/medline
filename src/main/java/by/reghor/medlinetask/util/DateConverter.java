package by.reghor.medlinetask.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateConverter {

    private static final String DATE_FORMAT = "MMM dd, yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static String format(LocalDate date) {
        return date.format(FORMATTER);
    }
}

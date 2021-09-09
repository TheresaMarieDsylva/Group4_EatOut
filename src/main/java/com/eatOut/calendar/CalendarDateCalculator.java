package com.eatOut.calendar;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CalendarDateCalculator implements IDateCalculator {
    private Calendar myCalendar;

    public CalendarDateCalculator() {
        myCalendar = Calendar.getInstance();
    }

    @Override
    public LocalDateTime parseToLocalDateTime(String date) {
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(date, localDateTimeFormatter);
    }

    @Override
    public LocalDateTime parseStringToDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(date,dateTimeFormatter);
    }

    @Override
    public LocalDateTime parseToISODateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(date,dateTimeFormatter);
    }

    @Override
    public String parseLocalDateTimeToStandardDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

    @Override
    public String convertUtcToCalendarDate(String endDate) {
        LocalDateTime utcDate = parseToISODateTime(endDate);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(utcDate.toInstant(ZoneOffset.UTC),
                myCalendar.getTimeZone().toZoneId());
        return parseLocalDateTimeToStandardDateTime(localDateTime);
    }
}

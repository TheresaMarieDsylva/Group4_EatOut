package com.eatOut.calendar;

import java.time.LocalDateTime;

public interface IDateCalculator {
    LocalDateTime parseToLocalDateTime(String date);
    LocalDateTime parseStringToDateTime(String date);
    LocalDateTime parseToISODateTime(String date);
    String parseLocalDateTimeToStandardDateTime(LocalDateTime localDateTime);
    String convertUtcToCalendarDate(String endDate);
}

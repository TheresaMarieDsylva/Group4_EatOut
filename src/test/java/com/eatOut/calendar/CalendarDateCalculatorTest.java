package com.eatOut.calendar;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CalendarDateCalculatorTest {
    private Calendar myCalendar;
    private static IDateCalculator dateCalculator;

    public CalendarDateCalculatorTest() {
        myCalendar = Calendar.getInstance();
    }

    @BeforeClass
    public static void setUp() {
        dateCalculator = new CalendarDateCalculator();
    }

    @Test
    public void parseStringToLocalDateTimeTest() {
        LocalDateTime dateTime = dateCalculator.parseToLocalDateTime("2020-04-08T22:00");
        Assert.assertNotNull(dateTime);
    }

    @Test
    public void parseStringToISODateTimeTest() {
        LocalDateTime dateTime = dateCalculator.parseToISODateTime("2020-04-08T22:00");
        Assert.assertNotNull(dateTime);
    }

    @Test
    public void parseStringToDateTimeTest() {
        LocalDateTime dateTime = dateCalculator.parseStringToDateTime("04-04-2021 22:00:00");
        Assert.assertNotNull(dateTime);
    }

    @Test
    public void parseLocalDateTimeToStandardDateTimeTest() {
        LocalDateTime dateTime = dateCalculator.parseToLocalDateTime("2021-04-08T22:00");
        String date = dateCalculator.parseLocalDateTimeToStandardDateTime(dateTime);
        Assert.assertEquals(date, "08-04-2021 10:00:00");
    }

    @Test
    public void convertUtcToCalendarDate() {
        String date = dateCalculator.convertUtcToCalendarDate("2021-04-08T23:45");
        Assert.assertNotEquals(date, "08-04-2021 23:45:00" );
    }

    @AfterClass
    public static void tearDown() {
        dateCalculator = null;
    }
}

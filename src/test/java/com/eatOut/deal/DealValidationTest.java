package com.eatOut.deal;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;
import org.junit.*;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DealValidationTest {
    IDealValidation dealValidation;
    IDateCalculator dateCalculator;

    @Before()
    public void init() {
        dealValidation = new DealValidation();
        dateCalculator = new CalendarDateCalculator();
    }

    @Test
    public void checkUserInputForNewDeal() throws Exception {
        List<DealOffer> dealOffers = new ArrayList<>();
        DealOffer dealOffer = new DealOffer();
        dealOffer.setRestaurantId("15");
        dealOffer.setRestaurantName("Waffle House");
        dealOffer.setDealType(BookType.DINING);
        dealOffer.setDealItem("Common seater");
        dealOffer.setOldPrice(350.00);
        dealOffer.setDealPrice(150.00);
        dealOffer.setDealEndDate("05-04-2021 17:30:00");
        dealOffers.add(dealOffer);
        LocalDateTime dateTime = LocalDateTime.parse("2021-04-02T19:36", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        boolean result = dealValidation.checkUserInputForDeal(dealOffers, 16, "DINING", "Private seater", dateTime);
        Assert.assertTrue(result);
    }

    @Test
    public void checkUserInputForExistingDeal() {
        List<DealOffer> dealOffers = new ArrayList<>();
        DealOffer dealOffer = new DealOffer();
        dealOffer.setRestaurantId("15");
        dealOffer.setRestaurantName("Waffle House");
        dealOffer.setDealType(BookType.DINING);
        dealOffer.setDealItem("Common seater");
        dealOffer.setOldPrice(350.00);
        dealOffer.setDealPrice(150.00);
        dealOffer.setDealEndDate("05-04-2021 17:30:00");
        dealOffers.add(dealOffer);
        LocalDateTime dateTime = LocalDateTime.parse("2021-04-02T19:36", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        Exception error = Assertions.assertThrows(Exception.class, () -> {
                dealValidation.checkUserInputForDeal(dealOffers, 15, "DINING", "Common seater", dateTime);
        });
        Assertions.assertTrue(error.getMessage().contains("Deal information already exists"));
    }

    @Test
    public void validateDealEndDateForInvalid() {
        LocalDateTime dateTime = LocalDateTime.parse("2020-04-08T22:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        Assert.assertFalse("Date is not within 2 days from current date", dealValidation.validateDealEndDate(dateTime));
    }

    @Test
    public void validateDealEndDateForSameDay() {
        LocalDateTime dateTime = LocalDateTime.parse("2021-04-01T19:36", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        Assert.assertFalse("Date is not within 2 days from current date", dealValidation.validateDealEndDate(dateTime));
    }

    @Test
    public void filterExpiredDealTest() {
        List<DealOffer> dealOffers = new ArrayList<>();
        DealOffer dealOffer = new DealOffer();
        dealOffer.setRestaurantId("15");
        dealOffer.setRestaurantName("Waffle House");
        dealOffer.setDealType(BookType.DINING);
        dealOffer.setDealItem("Common seater");
        dealOffer.setOldPrice(350.00);
        dealOffer.setDealPrice(150.00);
        dealOffer.setDealEndDate("05-04-2021 17:30:00");
        dealOffers.add(dealOffer);
        dealOffers = dealValidation.filterExpiredDeals(dealOffers, dateCalculator);
        Assert.assertEquals(0, dealOffers.size());
    }
}

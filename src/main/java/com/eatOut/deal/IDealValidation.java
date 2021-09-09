package com.eatOut.deal;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;

import java.time.LocalDateTime;
import java.util.List;

public interface IDealValidation {
    boolean checkUserInputForDeal(List<DealOffer> dealOffers, int restaurantId,
                                  String selectedDealType, String dealItem,
                                  LocalDateTime endDateTime) throws Exception;
    boolean validateDealEndDate(LocalDateTime endDateTime);
    List<DealOffer> filterExpiredDeals(List<DealOffer> dealOffers, IDateCalculator dateCalculator);
}

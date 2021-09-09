package com.eatOut.deal;

import com.eatOut.calendar.IDateCalculator;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DealValidation implements IDealValidation {

    public boolean checkUserInputForDeal(List<DealOffer> dealOffers,
                                         int restaurantId, String selectedDealType,
                                         String dealItem, LocalDateTime endDateTime) throws Exception {
        boolean dealAlreadyExits = checkIfDealIsRepeated(dealOffers, restaurantId, selectedDealType, dealItem, endDateTime);
        boolean isDealExpiryDateValid = validateDealEndDate(endDateTime);
        if(dealAlreadyExits) {
            throw new Exception("Deal information already exists");
        }
        if (!isDealExpiryDateValid) {
            throw new Exception("The deal end date should be within 2 days");
        }
        return true;
    }

    private boolean checkIfDealIsRepeated(List<DealOffer> dealOffers,
                                          int restaurantId, String selectedDealType,
                                          String dealItem, LocalDateTime endDateTime) {
        Iterator<DealOffer> iter = dealOffers.iterator();
        while(iter.hasNext()) {
            DealOffer dOffer = iter.next();
            if(dOffer.getRestaurantId().equals(String.valueOf(restaurantId)) &&
                    dOffer.getDealType().toString().equals(selectedDealType) &&
                    dOffer.getDealItem().equals(dealItem)) {
                boolean isActive = endDateTime.isAfter(LocalDateTime.now());
                return isActive;
            }
        }
        return false;
    }

    public boolean validateDealEndDate(LocalDateTime endDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime maxDealEndDateTime = currentDateTime.plusDays(2);
        boolean isBefore = endDateTime.isBefore(maxDealEndDateTime);
        boolean isAfter = endDateTime.isAfter(currentDateTime);
        boolean isNotEqual = !endDateTime.isEqual(currentDateTime);
        return isBefore && isAfter && isNotEqual;
    }

    public List<DealOffer> filterExpiredDeals(List<DealOffer> dealOffers, IDateCalculator dateCalculator) {
        dealOffers = dealOffers.stream().filter(dealOffer -> {
            LocalDateTime endDateTime = dateCalculator.parseStringToDateTime(dealOffer.getDealEndDate());
            return validateDealEndDate(endDateTime);
        }).collect(Collectors.toList());
        return dealOffers;
    }
}
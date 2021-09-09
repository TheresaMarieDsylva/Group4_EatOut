package com.eatOut.deal;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.sql.Timestamp;
import java.util.*;

public class DealDAO implements IDealDAO {
    private IDateCalculator dateCalculator;
    private List<DealOffer> dealOffers;

    public DealDAO() {
        dealOffers = new ArrayList<>();
        dateCalculator = new CalendarDateCalculator();
    }

    @Override
    public int addDealOffer(int restaurantId, Double price, String dealType, String dealItem, Double oldPrice, Timestamp endDate) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertDealOfferItems(?,?,?,?,?,?)");
            storedProcedure.setParameter(1, restaurantId);
            storedProcedure.setParameter(2, price);
            storedProcedure.setParameter(3, dealType);
            storedProcedure.setParameter(4, dealItem);
            storedProcedure.setParameter(5, oldPrice);
            storedProcedure.setParameter(6, endDate);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Unable to add dealItem " + dealItem);
        }
        return row;
    }

    @Override
    public List<DealOffer> loadDeals() throws Exception {
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getDealOfferItems()");
            List<Map<String, Object>> dealTable = storedProcedure.executeAndFetchTable();
            dealOffers = new ArrayList<>();
            dealOffers = getDeals(dealTable);
        } catch (Exception ex) {
            throw new Exception("Error in loading deals " + ex.getMessage());
        }
        return dealOffers;
    }

    private List<DealOffer> getDeals(List<Map<String, Object>> dealTable) {
        for (Map<String, Object> row : dealTable) {
            DealOffer dealOffer = new DealOffer();
            dealOffer.setRestaurantId(row.get("restaurant_id").toString());
            dealOffer.setRestaurantName(row.get("restaurant_name").toString());
            dealOffer.setDealType(setParameterDealType(row.get("deal_type").toString()));
            dealOffer.setDealId(row.get("deal_id").toString());
            dealOffer.setDealItem(row.get("deal_item").toString());
            dealOffer.setDealPrice(Double.parseDouble(row.get("new_price").toString()));
            dealOffer.setOldPrice(Double.parseDouble(row.get("old_price").toString()));
            dealOffer.setDealEndDate(dateCalculator.convertUtcToCalendarDate(row.get("deal_end_date").toString()));
            dealOffers.add(dealOffer);
        }
        return dealOffers;
    }

    private BookType setParameterDealType(String dealType) {
        BookType bookType = null;
        if (dealType.equalsIgnoreCase(BookType.DINING.toString())) {
            bookType = BookType.DINING;
        }
        else if(dealType.equalsIgnoreCase(BookType.TAKEAWAY.toString())) {
            bookType = BookType.TAKEAWAY;
        }
        return bookType;
    }
}
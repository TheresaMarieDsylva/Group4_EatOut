package com.eatOut.deal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DealMockDAO implements IDealDAO {
    List<DealOffer> dealOffers;

    public DealMockDAO() {
        dealOffers = new ArrayList<>();
    }

    @Override
    public int addDealOffer(int restaurantId, Double price, String dealType, String dealItem, Double oldPrice, Timestamp endDate) {
        if(restaurantId != 0 && !Double.isNaN(price) && null != endDate && null != dealItem) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<DealOffer> loadDeals() {
        DealOffer dealOffer = new DealOffer();
        dealOffer.setRestaurantId("15");
        dealOffer.setRestaurantName("Waffle House");
        dealOffer.setDealType(BookType.DINING);
        dealOffer.setDealItem("Common seater");
        dealOffer.setOldPrice(350.00);
        dealOffer.setDealPrice(150.00);
        dealOffer.setDealEndDate("05-04-2021 17:30:00");
        dealOffers.add(dealOffer);
        dealOffer = new DealOffer();
        dealOffer.setRestaurantId("10");
        dealOffer.setRestaurantName("Starbucks");
        dealOffer.setDealType(BookType.TAKEAWAY);
        dealOffer.setDealItem("All Canadian");
        dealOffer.setOldPrice(450.00);
        dealOffer.setDealPrice(200.00);
        dealOffer.setDealEndDate("02-04-2021 17:30:00");
        dealOffers.add(dealOffer);
        return dealOffers;
    }
}

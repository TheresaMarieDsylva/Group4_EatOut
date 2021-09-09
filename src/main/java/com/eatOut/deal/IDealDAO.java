package com.eatOut.deal;

import java.sql.Timestamp;
import java.util.List;

public interface IDealDAO {
    int addDealOffer(int restaurantId, Double price, String dealType, String dealItem, Double oldPrice, Timestamp endDate) throws Exception;
    List<DealOffer> loadDeals() throws Exception;
}

package com.eatOut.deal;

import java.util.List;
import java.util.Map;

import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;

public interface IDealOffer {
    int createDeal(IDealDAO dealDAO, String selectedDealType, int restaurantId, String newPrice, String dealItem, String dealOldPrice, String endDate) throws Exception;
    List<DealOffer> loadValidDeals(IDealDAO dealDAO) throws Exception;
    List<String> getRestaurantCountries(IRestaurantOperation restaurantService) throws Exception;
    List<Restaurant> loadRestaurantsByLocation(IRestaurantOperation restaurantService, String location) throws Exception;
    Map<String, Double> loadRestaurantItemsByDealType(Deal deal) throws Exception;
}

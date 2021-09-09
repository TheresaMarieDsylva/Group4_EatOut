package com.eatOut.userhome;

import java.util.List;
import java.util.Map;

import com.eatOut.deal.IDealDAO;
import com.eatOut.deal.IDealOffer;
import com.eatOut.restaurant.Restaurant;

public interface ICustomerOperations {
	
	List<Restaurant> getRecommendedListFrUsr(int userId);

	Map<Integer, Double> getRestrntsBookedByCust(int userId);

	Map<Integer, Map<Integer, Double>> getUsersFrRestrnts(String restaurantIds);

	Map<Integer, Double> getRestaurantsRatedByUsr(Integer userId, Double maxRatingsValue);

	Map<Integer, Double> getHighestRatedRestrntFrUsr(int userId);

	List<String> loadRestaurantByDeals(IDealOffer dealOffer, IDealDAO dealDAO);

	int applyDeals(String dealId, String price, String restaurantId, String customerId);

	List<String> viewDealsByUser(String customerId);

	int calculateCustomerWallet(int customerId);
}

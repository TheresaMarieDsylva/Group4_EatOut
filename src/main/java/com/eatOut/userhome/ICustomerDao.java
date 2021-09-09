package com.eatOut.userhome;

import java.util.List;
import java.util.Map;

public interface ICustomerDao {

	Map<Integer, Double> getRestrntsBookedByCust(int userId);

	Map<Integer, Map<Integer, Double>> getUsersFrRestrnts(String restaurantIds);

	Map<Integer, Double> getRestaurantsRatedByUsr(Integer similarUser, Double maxRatingsValue);

	Map<Integer, Double> getHighestRatedRestrntFrUsr(int userId);
	
	int applyDeal(String dealId, String restaurantId, String customerId);
	
	List<String> viewDealsByUser(String customerId) ;

	List<Integer> loadCouponCustomerWallet (int customerId);

	int loadBalanceCustomerWallet (int customerId);
}

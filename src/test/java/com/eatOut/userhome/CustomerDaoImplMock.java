package com.eatOut.userhome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDaoImplMock implements ICustomerDao{

	@Override
	public Map<Integer, Double> getRestrntsBookedByCust(int userId) {
		return new HashMap<>();
	}

	@Override
	public Map<Integer, Map<Integer, Double>> getUsersFrRestrnts(String restaurantIds) {
		return new HashMap<>();
	}

	@Override
	public Map<Integer, Double> getRestaurantsRatedByUsr(Integer similarUser, Double maxRatingsValue) {
		return new HashMap<>();
	}

	@Override
	public Map<Integer, Double> getHighestRatedRestrntFrUsr(int userId) {
		return new HashMap<>();
	}

	@Override
	public int applyDeal(String dealId, String restaurantId, String customerId) {
		return 0;
	}

	@Override
	public List<String> viewDealsByUser(String customerId) {
		return new ArrayList<>();
	}

	@Override
	public List<Integer> loadCouponCustomerWallet(int customerId) {
		List<Integer> couponWallet = new ArrayList<>();
		couponWallet.add(400);
		couponWallet.add(550);
		couponWallet.add(670);
		return couponWallet;
	}

	@Override
	public int loadBalanceCustomerWallet(int customerId) {
		return 30;
	}

}

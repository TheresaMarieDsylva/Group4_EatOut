package com.eatOut.restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantDaoMock implements IRestaurantDao{

	@Override
	public List<Restaurant> getRestaurantsByLocationAndStatus(String city, String location, String status) {
		return new ArrayList<>();
	}

	@Override
	public List<Restaurant> searchRestaurant(String restaurantName, String city, String location, String status) {
		return new ArrayList<>();
	}

	@Override
	public Restaurant getRestaurantDtlsByRestId(Integer restaurantId) {
		return new Restaurant();
	}

	@Override
	public List<Restaurant> getRestaurantsByCountry(String location) {
		return new ArrayList<>();
	}

	@Override
	public List<String> getRestaurantCountries() {
		return new ArrayList<>();
	}

	@Override
	public Map<String, String> getRestaurantLocations() {
		return new HashMap<>();
	}

	@Override
	public List<Restaurant> getPendingRestaurantRequests() {
		return new ArrayList<>();
	}

}

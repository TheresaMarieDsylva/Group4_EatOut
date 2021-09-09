package com.eatOut.restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAddtnlDaoMock implements IRestaurantAddtnlDao{

	@Override
	public boolean addRestaurantAddtnlDtls(RestaurantAdditionalDtls restaurantAdditionalDtls) {
		return true;
	}

	@Override
	public RestaurantAdditionalDtls getRestaurantAddtnlDtls(int restaurantId) {
		return new RestaurantAdditionalDtls();
	}

	@Override
	public List<Restaurant> getRestaurantByBookingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city,
			String country, String status) {
		return new ArrayList<>();
	}

	@Override
	public List<Restaurant> getRestaurantBySeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city,
			String country, String status) {
		return new ArrayList<>();
	}

	@Override
	public List<Restaurant> getRstrntByBookingAndSeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls,
			String city, String country, String status) {
		return new ArrayList<>();
	}

	@Override
	public List<Reviews> getReviewsForRestaurant(int restaurantId) {
		return new ArrayList<>();
	}

	@Override
	public boolean addReviewsFrRestaurant(Reviews reviews) {
		return true;
	}

}

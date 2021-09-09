package com.eatOut.restaurant;

import java.util.List;

public interface IRestaurantAddtnlDao {

	boolean addRestaurantAddtnlDtls(RestaurantAdditionalDtls restaurantAdditionalDtls);
	
	RestaurantAdditionalDtls getRestaurantAddtnlDtls(int restaurantId);
	
	List<Restaurant> getRestaurantByBookingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status);

	List<Restaurant> getRestaurantBySeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status);

	List<Restaurant> getRstrntByBookingAndSeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status);
	
	List<Reviews> getReviewsForRestaurant(int restaurantId);

	boolean addReviewsFrRestaurant(Reviews reviews);

}

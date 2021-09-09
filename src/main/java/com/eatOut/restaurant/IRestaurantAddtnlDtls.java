package com.eatOut.restaurant;

import java.util.List;

public interface IRestaurantAddtnlDtls {
	
	boolean addRestaurantAddtnlDtls(RestaurantAdditionalDtls restaurantAdditionalDtls);
	
	RestaurantAdditionalDtls getRestaurantAddtnlDtls(int restaurantId);
	
	List<Restaurant> getRestaurantDtlsByFilters(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status);
	
	List<Restaurant> getRestaurantByBookingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status);
	
	List<Restaurant> getRestaurantBySeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status);
	
	List<Restaurant> getRstrntByBookingAndSeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status);
	
	boolean addReviewsFrRestaurant(Reviews reviews);
	
	List<Reviews> getReviewsForRestaurant(int restaurantId);

	RestaurantAdditionalDtls getRestaurantAddtnlDtlsObj();
}

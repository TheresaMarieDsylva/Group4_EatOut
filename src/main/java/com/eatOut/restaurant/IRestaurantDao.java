package com.eatOut.restaurant;

import java.util.List;
import java.util.Map;

public interface IRestaurantDao {

	List<Restaurant> getRestaurantsByLocationAndStatus(String city,String location,String status);

	List<Restaurant> searchRestaurant(String restaurantName, String city, String location, String status);

	Restaurant getRestaurantDtlsByRestId(Integer restaurantId);
	
	List<Restaurant> getRestaurantsByCountry(String location) ;
	
	List<String> getRestaurantCountries(); 

	Map<String, String> getRestaurantLocations();

	List<Restaurant> getPendingRestaurantRequests();
}

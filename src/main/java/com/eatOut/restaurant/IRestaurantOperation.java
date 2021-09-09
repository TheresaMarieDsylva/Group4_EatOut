package com.eatOut.restaurant;

import java.util.List;
import java.util.Map;

public interface IRestaurantOperation {


    List<Restaurant> getRestaurantsByLocationAndStatus(String city, String location, String status);

    List<Restaurant> searchRestaurant(String restaurantName, String city, String location, String status);

    Restaurant getRestaurantDtlsByRestId(Integer restaurantId);

    List<String> getRestaurantCountries(IRestaurantDao restaurantDao);

    List<Restaurant> getRestaurantDetailsByCountry(String country);

    Map<String, List<String>> getRestaurantLocations();

    List<Restaurant> loadRestaurantRequests(IRestaurantDao restaurantDao);
}

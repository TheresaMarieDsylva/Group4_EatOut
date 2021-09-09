package com.eatOut.restaurant;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RestaurantOperationsImplTest {

	RestaurantAbstractFactory restaurantAbstractFactory;
	IRestaurantDao restaurantDao;
	IRestaurantOperation restaurantOperations;
	
	@Before()
	public void init() {
		restaurantAbstractFactory=new RestaurantConcreteFactoryTest();
		restaurantDao=restaurantAbstractFactory.getRestaurantDAO();
		restaurantOperations=restaurantAbstractFactory.getRestaurantOperation(restaurantDao);
	}

	@Test
	public void getRestaurantsByLocationAndStatusTest() {
		List<Restaurant> restaurantList=restaurantOperations.getRestaurantsByLocationAndStatus("Halifax", "Canada", "Approved");
		Assert.assertNotNull(restaurantList);
	}
	
	@Test
	public void searchRestaurantTest() {
		List<Restaurant> restaurantList=restaurantOperations.searchRestaurant("Starbucks", "Winnipeg", "Ontario", "Approved");
		Assert.assertNotNull(restaurantList);
	}
	
	@Test
	public void getRestaurantDtlsByRestIdTest() {
		Restaurant restaurant=restaurantOperations.getRestaurantDtlsByRestId(1);
		Assert.assertNotNull(restaurant);
	}
	
	@Test
	public void getRestaurantCountriesTest() {
		List<String> restaurantCountries=restaurantOperations.getRestaurantCountries(restaurantDao);
		Assert.assertNotNull(restaurantCountries);
	}
	
	@Test
	public void getRestaurantDetailsByCountryTest() {
		List<Restaurant> restaurantsList=restaurantOperations.getRestaurantDetailsByCountry("Canada");
		Assert.assertNotNull(restaurantsList);
	}
	
	
	@Test
	public void getRestaurantLocations() {
		Map<String, List<String>> locationsMap=restaurantOperations.getRestaurantLocations();
		Assert.assertNotNull(locationsMap);
	}

	@Test
	public void getPendingRestaurantRequest() {
		List<Restaurant> restaurantList = restaurantOperations.loadRestaurantRequests(restaurantDao);
		Assert.assertNotNull(restaurantList);
	}


}

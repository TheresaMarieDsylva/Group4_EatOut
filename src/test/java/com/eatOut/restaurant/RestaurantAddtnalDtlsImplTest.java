package com.eatOut.restaurant;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RestaurantAddtnalDtlsImplTest{
	
	
	RestaurantAbstractFactory restaurantAbstractFactory;
	IRestaurantAddtnlDao restaurantAddtnlDao;
	IRestaurantAddtnlDtls restaurantAddtnlDtls;
	
	@Before()
	public void init() {
		restaurantAbstractFactory=new RestaurantConcreteFactoryTest();
		restaurantAddtnlDao=restaurantAbstractFactory.getRestuarantAddtnlDAO();
		restaurantAddtnlDtls=restaurantAbstractFactory.getRestaurantAddtnlDtls(restaurantAddtnlDao);
	}
	
	@Test
	public  void addRestaurantAddtnlDtlsTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls = new RestaurantAdditionalDtls();
		boolean restrntAdded=restaurantAddtnlDtls.addRestaurantAddtnlDtls(restaurantAdditionalDtls);
		Assert.assertTrue(restrntAdded);
	}
	
	@Test
	public void getRestaurantAddtnlDtlsTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=restaurantAddtnlDtls.getRestaurantAddtnlDtls(1);
		Assert.assertNotNull(restaurantAdditionalDtls);
	}

	
	@Test
	public void getRestaurantDtlsByFiltersTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		List<Restaurant> restaurantList=restaurantAddtnlDtls.getRestaurantDtlsByFilters(restaurantAdditionalDtls, "Halifax", "Canada", "Approved");
		Assert.assertNotNull(restaurantList);
	}

	@Test
	public void getRestaurantByBookingTypeTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		List<Restaurant> restaurantList=restaurantAddtnlDtls.getRestaurantByBookingType(restaurantAdditionalDtls, "Halifax", "Canada", "Approved");
		Assert.assertNotNull(restaurantList);
	}
	
	@Test
	public void getRestaurantBySeatingTypeTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		List<Restaurant> restaurantList=restaurantAddtnlDtls.getRestaurantBySeatingType(restaurantAdditionalDtls, "Halifax", "Canada", "Approved");
		Assert.assertNotNull(restaurantList);
	}
	
	@Test
	public void getRstrntByBookingAndSeatingTypeTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		List<Restaurant> restaurantList=restaurantAddtnlDtls.getRstrntByBookingAndSeatingType(restaurantAdditionalDtls, "Halifax", "Canada", "Approved");
		Assert.assertNotNull(restaurantList);
	}

	@Test
	public void addReviewsFrRestaurantTest() {
		Reviews reviews=new Reviews();
		boolean reviewsAdded=restaurantAddtnlDtls.addReviewsFrRestaurant(reviews);
		Assert.assertTrue(reviewsAdded);
	}
	
	@Test
	public void getReviewsForRestaurantTest() {
		List<Reviews> reviewsList=restaurantAddtnlDtls.getReviewsForRestaurant(1);
		Assert.assertNotNull(reviewsList);
	}

	@Test
	public void getRestaurantAddtnlDtlsObjTest() {
		RestaurantAdditionalDtls restaurantAdditionalDtls=restaurantAddtnlDtls.getRestaurantAddtnlDtlsObj();
		Assert.assertNotNull(restaurantAdditionalDtls);
	}



}

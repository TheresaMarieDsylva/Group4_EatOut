package com.eatOut.userhome;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.eatOut.restaurant.IRestaurantDao;
import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;
import com.eatOut.restaurant.RestaurantAbstractFactory;
import com.eatOut.restaurant.RestaurantConcreteFactoryTest;

public class CustomerOperationsImplTest {
	
	RestaurantAbstractFactory restaurantAbstractFactory;
	IRestaurantOperation restaurantOperation;
	IRestaurantDao restaurantDao;
	CustomerAbstractFactory customerAbstractFactory;
	ICustomerOperations customerOperations;
	ICustomerDao customerDao;
	
	@Before()
	public void init() {
		restaurantAbstractFactory=new RestaurantConcreteFactoryTest();
		restaurantDao=restaurantAbstractFactory.getRestaurantDAO();
		restaurantOperation=restaurantAbstractFactory.getRestaurantOperation(restaurantDao);
		customerAbstractFactory=new CustomerConcreteFactoryTest();
		customerDao=customerAbstractFactory.getCustomerDAO();
		customerOperations=customerAbstractFactory.getCustomerOperations(restaurantOperation, customerDao);
	}

	@Test
	public void getRecommendedListFrUsrTest() {
		List<Restaurant> restaurantList=customerOperations.getRecommendedListFrUsr(1);
		Assert.assertNotNull(restaurantList);
	}
	
	@Test
	public void getRestaurantsRatedByUsrTest() {
		Map<Integer, Double> bookingsMap=customerOperations.getRestaurantsRatedByUsr(1,5.0);
		Assert.assertNotNull(bookingsMap);
	}

	@Test
	public void getHighestRatedRestrntFrUsrTest() {
		Map<Integer, Double> bookingsMap=customerOperations.getHighestRatedRestrntFrUsr(1);
		Assert.assertNotNull(bookingsMap);
	}

	@Test
	public void getRestrntsBookedByCustTest() {
		Map<Integer, Double> bookingsMap=customerOperations.getRestrntsBookedByCust(1);
		Assert.assertNotNull(bookingsMap);
	}

	@Test
	public void getUsersFrRestrntsTest() {
		Map<Integer, Map<Integer, Double>> usersMap=customerOperations.getUsersFrRestrnts("1,2,3");
		Assert.assertNotNull(usersMap);
	}

	@Test
	public void calculateCustomerWalletTest() {
		int amount = customerOperations.calculateCustomerWallet(2);
		Assert.assertEquals(amount, 1590);
	}
}

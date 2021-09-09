package com.eatOut.usercustomer;

import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.userhome.CustomerOperationsImpl;
import com.eatOut.userhome.ICustomerDao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CustomerOperationsImplTest {
    private static CustomerOperationsImpl customerOperations;
    private static IRestaurantOperation restaurantService;
    private static ICustomerDao customerDao;

    @BeforeClass
    public static void setUp() {
        customerOperations = new CustomerOperationsImpl(restaurantService, customerDao);
    }

    @Test
    public void applyDealsForNewDealTest() {
        List<String> userDealIds = new ArrayList<>();
        userDealIds.add("1");
        userDealIds.add("2");
        Assert.assertFalse("Applying new deals for customer", customerOperations.checkIfDealRepeated(userDealIds, "7"));
    }

    @Test
    public void applyDealsForExistingDealTest() {
        List<String> userDealIds = new ArrayList<>();
        userDealIds.add("1");
        userDealIds.add("2");
        userDealIds.add("3");
        Assert.assertTrue("Applying existing deals for customer", customerOperations.checkIfDealRepeated(userDealIds, "3"));
    }

    @AfterClass
    public static void tearDown() {
        customerOperations = null;
    }
}

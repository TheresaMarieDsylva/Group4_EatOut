package com.eatOut.deal;

import com.eatOut.restaurant.*;

import org.junit.*;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DealOfferTest {
    RestaurantAbstractFactory restaurantAbstractFactory;
    DealAbstractFactory dealAbstractFactory;
    IDealOffer dealOffer;
    IDealDAO dealDAO;
    IRestaurantDao restaurantDao;
    IRestaurantOperation restaurantOperation;

    @Before()
    public void init() {
        restaurantAbstractFactory = new RestaurantConcreteFactoryTest();
        dealAbstractFactory = new DealConcreteFactoryTest();
        dealOffer = dealAbstractFactory.getCustomerDealOffers();
        dealDAO = dealAbstractFactory.getCustomerDealDAO();
        restaurantDao = restaurantAbstractFactory.getRestaurantDAO();
        restaurantOperation = restaurantAbstractFactory.getRestaurantOperation(restaurantDao);
    }

    @Test
    public void createExistingDealTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            dealOffer.createDeal(dealDAO, "TAKEAWAY", 10, "200",
                    "All Canadian", "400", "2021-04-02T17:30");
        });
        Assertions.assertFalse(error.getMessage().contains("Deal information already exists"));
    }

    @Test
    public void createInvalidEndDateDealTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            dealOffer.createDeal(dealDAO, "DINING", 5, "200",
                    "Common seater", "400", "2021-04-08T23:59");
        });
        Assertions.assertTrue(error.getMessage().contains("The deal end date should be within 2 days"));
    }

    @Test
    public void createValidDealTest() throws Exception {
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String endEndDate = now.format(localDateTimeFormatter);
        int response = dealOffer.createDeal(dealDAO, "DINING", 5, "200",
                "Common seater", "400", endEndDate);
        Assert.assertTrue(response > 0);
    }

    @Test
    public void loadValidDeals() throws Exception {
        List<DealOffer> dealOffers = dealOffer.loadValidDeals(dealDAO);
        Assert.assertNotNull(dealOffers);
    }

    @Test
    public void loadRestaurantsByLocation() throws Exception {
        List<Restaurant> restaurants = dealOffer.loadRestaurantsByLocation(restaurantOperation, "Canada");
        Assert.assertNotNull(restaurants);
    }

    @Test
    public void loadRestaurantsByInvalidLocation() throws Exception {
        List<Restaurant> restaurants = dealOffer.loadRestaurantsByLocation(restaurantOperation, "Japan");
        Assert.assertTrue(restaurants.isEmpty());
    }

    @Test
    public void loadRestaurantItemsForDining() throws Exception {
        Map<String, Double> items = dealOffer.loadRestaurantItemsByDealType(new DiningMock());
        Assert.assertTrue(items.size() > 0);
    }

    @Test
    public void loadRestaurantItemsForTakeaway() throws Exception {
        Map<String, Double> items = dealOffer.loadRestaurantItemsByDealType(new TakeawayMock());
        Assert.assertTrue(items.size() > 0);
    }

    @Test
    public void getRestaurantCountries() throws Exception {
        List<String> countries = dealOffer.getRestaurantCountries(restaurantOperation);
        Assert.assertNotNull(countries);
    }
}

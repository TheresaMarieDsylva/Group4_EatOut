package com.eatOut.restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eatOut.notifier.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

public class RestaurantDaoImpl implements IRestaurantDao {

    private static Logger logger = LogManager.getLogger(RestaurantDaoImpl.class);

    @Override
    public List<Restaurant> getRestaurantsByLocationAndStatus(String city, String location, String status) {
        List<Restaurant> restaurantList = new ArrayList<>();

        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getAllRestaurants(?,?,?)");
            storedProcedure.setParameter(1, city);
            storedProcedure.setParameter(2, location);
            storedProcedure.setParameter(3, status);
            List<Map<String, Object>> tablelist = storedProcedure.executeAndFetchTable();
            restaurantList = getRestaurantList(tablelist);
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant details", e);
        }

        return restaurantList;
    }


    @Override
    public List<Restaurant> searchRestaurant(String restaurantName, String city, String location, String status) {
        List<Restaurant> restaurantList = new ArrayList<>();

        try {
            IStoredProcedure storedProcedure = new StoredProcedure("searchRestaurant(?,?,?,?)");
            storedProcedure.setParameter(1, city);
            storedProcedure.setParameter(2, location);
            storedProcedure.setParameter(3, status);
            storedProcedure.setParameter(4, restaurantName);
            List<Map<String, Object>> tablelist = storedProcedure.executeAndFetchTable();
            restaurantList = getRestaurantList(tablelist);
        } catch (SQLException e) {
            logger.error("SQL error occured while searching restaurant details", e);
        }

        return restaurantList;
    }

    private List<Restaurant> getRestaurantList(List<Map<String, Object>> tablelist) {
        List<Restaurant> restaurantList = new ArrayList<>();

        for (Map<String, Object> row : tablelist) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId((Integer) row.get("restaurant_id"));
            restaurant.setRestaurantName((String) row.get("restaurant_name"));
            restaurant.setPhoneNumber((Long) row.get("phone_number"));
            restaurant.setAddress((String) row.get("full_address"));
            restaurant.setCity((String) row.get("city"));
            restaurant.setCountry((String) row.get("country"));
            restaurant.setEmail((String) row.get("restaurant_email"));
            restaurantList.add(restaurant);
        }

        return restaurantList;
    }


    @Override
    public Restaurant getRestaurantDtlsByRestId(Integer restaurantId) {
        Restaurant restaurant = new Restaurant();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantDtlsByRestId(?)");
            storedProcedure.setParameter(1, restaurantId);
            List<Map<String, Object>> tablelist = storedProcedure.executeAndFetchTable();
            if (tablelist.isEmpty()) {
                logger.debug("No details found for the restaurant");
            } else {
                restaurant = getRestaurantList(tablelist).get(0);
            }
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant details", e);
        }
        return restaurant;
    }

    @Override
    public List<String> getRestaurantCountries() {
        List<String> countryList = new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantCountries()");
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            countryList = getCountryList(tableList);
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant countries", e);
        }
        return countryList;
    }

    private List<String> getCountryList(List<Map<String, Object>> tableList) {
        List<String> country = new ArrayList<>();
        for (Map<String, Object> row : tableList) {
            country.add(row.get("country").toString());
        }
        return country;
    }

    @Override
    public List<Restaurant> getRestaurantsByCountry(String location) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantsByCountry(?)");
            storedProcedure.setParameter(1, location);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            restaurantList = getRestaurantDetails(tableList);
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant details by country", e);
        }
        return restaurantList;
    }

    private List<Restaurant> getRestaurantDetails(List<Map<String, Object>> tableList) {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (Map<String, Object> row : tableList) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId((Integer) row.get("restaurant_id"));
            restaurant.setRestaurantName((String) row.get("restaurant_name"));
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }


    @Override
    public Map<String, String> getRestaurantLocations() {
        Map<String, String> restaurantLocations = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantLocations()");
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> row : tableList) {
                String country = (String) row.get("country");
                String city = (String) row.get("city");
                restaurantLocations.put(country, city);
            }
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant locations", e);
        }

        return restaurantLocations;
    }

    @Override
    public List<Restaurant> getPendingRestaurantRequests() {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getPendingRestaurantRequests(?)");
            storedProcedure.setParameter(1, Status.WAITING.toString());
            List<Map<String, Object>> restaurantTable = storedProcedure.executeAndFetchTable();
            restaurantList = getRestaurantList(restaurantTable);
        } catch (SQLException e) {
            logger.error("SQL error occured while fetching restaurant locations", e);
        }
        return restaurantList;
    }
}

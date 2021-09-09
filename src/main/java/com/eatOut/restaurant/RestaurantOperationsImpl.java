package com.eatOut.restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestaurantOperationsImpl implements IRestaurantOperation{

    private static Logger logger = LogManager.getLogger(RestaurantOperationsImpl.class);

    IRestaurantAddtnlDtls restaurantAddtnlDtls;
    IRestaurantDao restaurantDao;

    public RestaurantOperationsImpl(IRestaurantAddtnlDtls restaurantAddtnlDtls, IRestaurantDao restaurantDao) {
        this.restaurantAddtnlDtls = restaurantAddtnlDtls;
        this.restaurantDao = restaurantDao;
    }

    public RestaurantOperationsImpl(IRestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public List<Restaurant> getRestaurantsByLocationAndStatus(String city,String location,String status) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            restaurantList = restaurantDao.getRestaurantsByLocationAndStatus(city,location,status);
            for(Restaurant tmpRestaurant: restaurantList) {
                setOtherRestrntDtls(tmpRestaurant);
            }
        } catch (Exception e) {
            logger.error("Exception occured while fetching restaurants details by location and status",e);
        }
        return restaurantList;
    }


    @Override
    public List<Restaurant> searchRestaurant(String restaurantName,String city,String location,String status) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            restaurantList = restaurantDao.searchRestaurant(restaurantName,city,location,status);
            for(Restaurant tmpRestaurant: restaurantList) {
                setOtherRestrntDtls(tmpRestaurant);
            }
        } catch (Exception e) {
            logger.error("Exception occured while searching restaurants",e);
        }
        return restaurantList;
    }

    @Override
    public Restaurant getRestaurantDtlsByRestId(Integer restaurantId) {
        Restaurant restaurant = new Restaurant();
        try {
            restaurant = restaurantDao.getRestaurantDtlsByRestId(restaurantId);
            if(restaurant.getRestaurantId()>0) {
                setOtherRestrntDtls(restaurant);
            }
        } catch (Exception e) {
            logger.error("Exception occured while fetching restaurant details",e);
        }
        return restaurant;
    }

    @Override
    public List<String> getRestaurantCountries(IRestaurantDao restaurantDao) {
        List<String> restaurantCountries=new ArrayList<>();
        try {
            restaurantCountries= restaurantDao.getRestaurantCountries();
        } catch (Exception e) {
            logger.error("Exception occured while fetching restaurant countries",e);
        }
        return restaurantCountries;
    }

    @Override
    public List<Restaurant> getRestaurantDetailsByCountry(String country)  {
        List<Restaurant> restaurantCountries=new ArrayList<>();
        try {
            restaurantCountries= restaurantDao.getRestaurantsByCountry(country);
        } catch (Exception e) {
            logger.error("Exception occured while fetching restaurant details by country",e);
        }
        return restaurantCountries;
    }

    private void setOtherRestrntDtls(Restaurant tmpRestaurant) {
        tmpRestaurant.loadReviewList(restaurantAddtnlDtls);
        tmpRestaurant.loadRestaurantAdditionalDtls(restaurantAddtnlDtls);
        tmpRestaurant.loadOverAllRatings();
    }

    @Override
    public Map<String, List<String>> getRestaurantLocations() {
        Map<String, List<String>> locationsMap=new HashMap<>();
        try {
            Map<String,String> restaurantLocations=restaurantDao.getRestaurantLocations();
            for(Entry<String,String> mapEntry:restaurantLocations.entrySet()) {
                String[] cities=mapEntry.getValue().trim().split(",");
                List<String> cityList=Arrays.asList(cities);
                locationsMap.put(mapEntry.getKey(), cityList);
            }
        } catch (Exception e) {
            logger.error("Exception occured while fetchiing restaurant locations",e);
        }
        return locationsMap;
    }

    @Override
    public List<Restaurant> loadRestaurantRequests(IRestaurantDao restaurantDao) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            restaurants = restaurantDao.getPendingRestaurantRequests();
        } catch (
                Exception e) {
            logger.error("Exception occured while fetching restaurant requests", e);
        }
        return restaurants;
    }
}

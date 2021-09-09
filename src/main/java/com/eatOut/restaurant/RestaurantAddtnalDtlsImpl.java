package com.eatOut.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestaurantAddtnalDtlsImpl implements IRestaurantAddtnlDtls {
	
	private static Logger logger = LogManager.getLogger(RestaurantAddtnalDtlsImpl.class);
	
	IRestaurantAddtnlDao restaurantAddtnlDao;
	
	public RestaurantAddtnalDtlsImpl(IRestaurantAddtnlDao restaurantAddtnlDao) {
		this.restaurantAddtnlDao = restaurantAddtnlDao;
	}

	@Override
	public boolean addRestaurantAddtnlDtls(RestaurantAdditionalDtls restaurantAdditionalDtls) {
		try {
			return restaurantAddtnlDao.addRestaurantAddtnlDtls(restaurantAdditionalDtls);
		} catch (Exception e) {
			logger.error("Exception occured while adding restaurant details",e);
			return false;
		}
		
	}

	@Override
	public RestaurantAdditionalDtls getRestaurantAddtnlDtls(int restaurantId) {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		try {
			restaurantAdditionalDtls= restaurantAddtnlDao.getRestaurantAddtnlDtls(restaurantId);
		} catch (Exception e) {
			logger.error("Exception occured while fetching restaurant additional details",e);
		}
		return restaurantAdditionalDtls;
	}


	@Override
	public List<Restaurant> getRestaurantDtlsByFilters(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status) {
		List<Restaurant> restaurantDtls=new ArrayList<>();
		try {
			boolean bookingType=restaurantAdditionalDtls.isBookingFlag();
			boolean seatingType=restaurantAdditionalDtls.isSeatingFlag();
			if ((bookingType) && (seatingType)) {
				restaurantDtls= getRstrntByBookingAndSeatingType(restaurantAdditionalDtls,city,country,status);
			}
			else if(bookingType) {
				restaurantDtls= getRestaurantByBookingType(restaurantAdditionalDtls,city,country,status);
			}else if(seatingType) {
				restaurantDtls= getRestaurantBySeatingType(restaurantAdditionalDtls,city,country,status);
			}
		} catch (Exception e) {
			logger.error("Exceptioon occured while filtering restaurant details",e);
		}
		
		return restaurantDtls;
	}


	@Override
	public List<Restaurant> getRestaurantByBookingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		try {
			restaurantList=restaurantAddtnlDao.getRestaurantByBookingType(restaurantAdditionalDtls,city,country,status);
			getReviews(restaurantList);
		} catch (Exception e) {
			logger.error("Exceptioin occured while filtering restaurant details by booking type",e);
		}
		return restaurantList;
	}

	@Override
	public List<Restaurant> getRestaurantBySeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		try {
			restaurantList=restaurantAddtnlDao.getRestaurantBySeatingType(restaurantAdditionalDtls,city,country,status);
			getReviews(restaurantList);
		} catch (Exception e) {
			logger.error("Exception occured while  fetching restaurant details by seating type",e);
		}
		return restaurantList;
	}

	@Override
	public List<Restaurant> getRstrntByBookingAndSeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls, String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		try {
			restaurantList=restaurantAddtnlDao.getRstrntByBookingAndSeatingType(restaurantAdditionalDtls,city,country,status);
			getReviews(restaurantList);
		} catch (Exception e) {
			logger.error("Exception occured while filtering resturants",e);
		}
		return restaurantList;
	}

	private void getReviews(List<Restaurant> restaurantList) {
		for(Restaurant tmpRestaurant: restaurantList) {
			List<Reviews> reviewsList=getReviewsForRestaurant(tmpRestaurant.getRestaurantId());
			tmpRestaurant.setReviewlist(reviewsList);
			tmpRestaurant.setRestaurantAdditionalDtls(getRestaurantAddtnlDtls(tmpRestaurant.getRestaurantId()));
			tmpRestaurant.setOverallRatings(Math.round((reviewsList.stream().mapToDouble((x) -> x.getRatingsValue()).average()).orElse(0.0)));
		}
	}
	
	
	@Override
	public List<Reviews> getReviewsForRestaurant(int restaurantId) {
		List<Reviews> reviewsList=new ArrayList<>();
		try {
			reviewsList= restaurantAddtnlDao.getReviewsForRestaurant(restaurantId);
		} catch (Exception e) {
			logger.error("Exception occured while fetching reviews for restaurant",e);
		}
		return reviewsList;
	}

	@Override
	public boolean addReviewsFrRestaurant(Reviews reviews) {
		try {
			return restaurantAddtnlDao.addReviewsFrRestaurant(reviews);
		} catch (Exception e) {
			logger.error("Exception occured while adding reviews",e);
			return false;
		}
	}

	@Override
	public RestaurantAdditionalDtls getRestaurantAddtnlDtlsObj() {
		return new RestaurantAdditionalDtls();
	}
}

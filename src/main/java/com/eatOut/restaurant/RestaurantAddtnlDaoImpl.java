package com.eatOut.restaurant;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

public class RestaurantAddtnlDaoImpl implements IRestaurantAddtnlDao{

	private static Logger logger = LogManager.getLogger(RestaurantAddtnlDaoImpl.class);
	
	@Override
	public boolean addRestaurantAddtnlDtls(RestaurantAdditionalDtls restaurantAdditionalDtls) {
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("addRestaurantAddtnlDtls (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			storedProcedure.setParameter(1,restaurantAdditionalDtls.getRestaurantId());
			storedProcedure.setParameter(2, restaurantAdditionalDtls.isDiningOption());
			storedProcedure.setParameter(3, restaurantAdditionalDtls.isTakeAwayOption());
			storedProcedure.setParameter(4, restaurantAdditionalDtls.isCommonSeatsOption());
			storedProcedure.setParameter(5,restaurantAdditionalDtls.isTerraceSeatsOption());
			storedProcedure.setParameter(6, restaurantAdditionalDtls.isWindowSeatsOption());
			storedProcedure.setParameter(7, restaurantAdditionalDtls.isLoungeSeatsOption());
			storedProcedure.setParameter(8, restaurantAdditionalDtls.isPrivateSeatsOption());
			storedProcedure.setParameter(9, restaurantAdditionalDtls.getCuisineType());
			storedProcedure.setParameter(10, restaurantAdditionalDtls.getOpeningTime());
			storedProcedure.setParameter(11, restaurantAdditionalDtls.getClosingTime());
			storedProcedure.setParameter(12, restaurantAdditionalDtls.getAdditionalComments());
			storedProcedure.setParameter(13, restaurantAdditionalDtls.getApproxPrice());
			storedProcedure.executeProcedure();
			return true;
		} catch (SQLException e) {
			logger.error("SQL error occured while adding restaurant details",e);
			return false;
		}
	}

	@Override
	public RestaurantAdditionalDtls getRestaurantAddtnlDtls(int restaurantId) {
		RestaurantAdditionalDtls restaurantAdditionalDtls=new RestaurantAdditionalDtls();
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("getRestaurantAddtnlDtls (?)");
			storedProcedure.setParameter(1, restaurantId);
			List<Map<String,Object>> table=storedProcedure.executeAndFetchTable();
			if(table.isEmpty()) {
				logger.error("No additional details found for the restaurant");
			}
			else {
				Map<String,Object> additionalDtlsMap=table.get(0);
				restaurantAdditionalDtls.setRestaurantId((Integer) additionalDtlsMap.get("restaurant_id"));
				restaurantAdditionalDtls.setDiningOption((Boolean) additionalDtlsMap.get("dining"));
				restaurantAdditionalDtls.setTakeAwayOption((Boolean) additionalDtlsMap.get("takeaway"));
				restaurantAdditionalDtls.setCommonSeatsOption((Boolean) additionalDtlsMap.get("common_seats_option"));
				restaurantAdditionalDtls.setTerraceSeatsOption((Boolean) additionalDtlsMap.get("terrace_seats_option"));
				restaurantAdditionalDtls.setWindowSeatsOption((Boolean) additionalDtlsMap.get("window_seats_option"));
				restaurantAdditionalDtls.setLoungeSeatsOption((Boolean) additionalDtlsMap.get("lounge_seats_option"));
				restaurantAdditionalDtls.setPrivateSeatsOption((Boolean) additionalDtlsMap.get("private_seats_option"));
				restaurantAdditionalDtls.setCuisineType((String) additionalDtlsMap.get("cuisine_type"));
				restaurantAdditionalDtls.setOpeningTime((Time) additionalDtlsMap.get("opening_time"));
				restaurantAdditionalDtls.setClosingTime((Time) additionalDtlsMap.get("closing_time"));
				restaurantAdditionalDtls.setStrOpeningTime(((Time) additionalDtlsMap.get("opening_time")).toString());
				restaurantAdditionalDtls.setStrClosingTime(((Time) additionalDtlsMap.get("closing_time")).toString());
				restaurantAdditionalDtls.setAdditionalComments((String) additionalDtlsMap.get("additional_comments"));
				restaurantAdditionalDtls.setApproxPrice((String) additionalDtlsMap.get("approx_price"));
			}
			
		} catch (SQLException e) {
			logger.error("SQL error occured while fetching restaurant additional details",e);
		}
		return restaurantAdditionalDtls;
	}

	@Override
	public List<Restaurant> getRestaurantByBookingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("filterRestaurantByBookingType (?,?,?,?,?)");
			storedProcedure.setParameter(1, restaurantAdditionalDtls.isDiningOption());
			storedProcedure.setParameter(2, restaurantAdditionalDtls.isTakeAwayOption());
			storedProcedure.setParameter(3, city);
			storedProcedure.setParameter(4, country);
			storedProcedure.setParameter(5, status);
			List<Map<String,Object>> table=storedProcedure.executeAndFetchTable();
			restaurantList=getRestaurantList(table);
		} 
		catch (SQLException e) {
			logger.error("SQL error occured while filtering restaurant details by booking type",e);
		}
		
		return restaurantList;
	}

	@Override
	public List<Restaurant> getRestaurantBySeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("filterRestaurantBySeatingType (?,?,?,?,?,?,?,?)");
			storedProcedure.setParameter(1, restaurantAdditionalDtls.isCommonSeatsOption());
			storedProcedure.setParameter(2, restaurantAdditionalDtls.isTerraceSeatsOption());
			storedProcedure.setParameter(3, restaurantAdditionalDtls.isWindowSeatsOption());
			storedProcedure.setParameter(4, restaurantAdditionalDtls.isLoungeSeatsOption());
			storedProcedure.setParameter(5, restaurantAdditionalDtls.isPrivateSeatsOption());
			storedProcedure.setParameter(6, city);
			storedProcedure.setParameter(7, country);
			storedProcedure.setParameter(8, status);
			List<Map<String,Object>> table=storedProcedure.executeAndFetchTable();
			restaurantList=getRestaurantList(table);
		} 
		catch (SQLException e) {
			logger.error("SQL error occured while filtering restaurant details by seating type",e);
		}
		
		return restaurantList;
	}

	@Override
	public List<Restaurant> getRstrntByBookingAndSeatingType(RestaurantAdditionalDtls restaurantAdditionalDtls,String city, String country, String status) {
		List<Restaurant> restaurantList=new ArrayList<>();
		
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("filterRstrntByBookingAndSeatingType (?,?,?,?,?,?,?,?,?,?)");
			storedProcedure.setParameter(1, restaurantAdditionalDtls.isDiningOption());
			storedProcedure.setParameter(2, restaurantAdditionalDtls.isTakeAwayOption());
			storedProcedure.setParameter(3, restaurantAdditionalDtls.isCommonSeatsOption());
			storedProcedure.setParameter(4, restaurantAdditionalDtls.isTerraceSeatsOption());
			storedProcedure.setParameter(5, restaurantAdditionalDtls.isWindowSeatsOption());
			storedProcedure.setParameter(6, restaurantAdditionalDtls.isLoungeSeatsOption());
			storedProcedure.setParameter(7, restaurantAdditionalDtls.isPrivateSeatsOption());
			storedProcedure.setParameter(8, city);
			storedProcedure.setParameter(9, country);
			storedProcedure.setParameter(10, status);
			List<Map<String,Object>> table=storedProcedure.executeAndFetchTable();
			restaurantList=getRestaurantList(table);
		} 
		catch (SQLException e) {
			logger.error("SQL error occured while filtering restaurant details",e);
		}
		
		return restaurantList;
	}

	
	private List<Restaurant> getRestaurantList(List<Map<String, Object>> tablelist) {
		List<Restaurant> restaurantList=new ArrayList<>();
		
		for(Map<String, Object> row:tablelist) {
			Restaurant restaurant=new Restaurant();
			restaurant.setRestaurantId((Integer) row.get("restaurant_id"));
			restaurant.setRestaurantName((String) row.get("restaurant_name"));
			restaurant.setPhoneNumber((Long)  row.get("phone_number"));
			restaurant.setAddress((String)  row.get("full_address"));
			restaurant.setCity((String)  row.get("city"));
			restaurant.setCountry((String)  row.get("country"));
			restaurant.setEmail((String)  row.get("restaurant_email"));
			restaurantList.add(restaurant);
		}
		
		return restaurantList;
	}
	
	@Override
	public List<Reviews> getReviewsForRestaurant(int restaurantId) {
		List<Reviews> reviewsList=new ArrayList<>();
		
		try {
			
			IStoredProcedure storedProcedure=new StoredProcedure("getReviewsForRestaurant(?)");
			storedProcedure.setParameter(1, restaurantId);
			List<Map<String, Object>> tablelist=storedProcedure.executeAndFetchTable();
			for(Map<String, Object> row:tablelist) {
				Reviews review=new Reviews();
				review.setReviewId((Integer) row.get("review_id"));
				review.setCustomerId((Integer) row.get("customer_id"));
				review.setCustomerName((String) row.get("customer_name"));
				review.setRestaurantId((Integer) row.get("restaurant_id"));
				review.setRatingsValue(Math.round((Float) row.get("ratings")));
				review.setComments((String) row.get("comments"));
				reviewsList.add(review);
			}
		} catch (SQLException e) {
			logger.error("SQL error occured while filtering restaurant details",e);
		}
		
		return reviewsList;
	}

	@Override
	public boolean addReviewsFrRestaurant(Reviews reviews) {
		
		try {
			IStoredProcedure storedProcedure=new StoredProcedure("insertRatingsAndReviews(?,?,?,?)");
			storedProcedure.setParameter(1, reviews.getCustomerId());
			storedProcedure.setParameter(2, reviews.getRestaurantId());
			storedProcedure.setParameter(3, reviews.getRatingsValue());
			storedProcedure.setParameter(4, reviews.getComments());
			storedProcedure.executeProcedure();
			return true;
		} catch (SQLException e) {
			logger.error("SQL error occured while adding reviews ",e);
			return false;
		}
		
	}
	
}

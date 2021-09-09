package com.eatOut.userhome;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

public class CustomerDaoImpl implements ICustomerDao {

	private static Logger logger = LogManager.getLogger(CustomerDaoImpl.class);
	
	private static final String RESTAURANT_ID="restaurant_id";
	private static final String BOOKING_COUNT="booking_count";
	private static final String CUSTOMER_ID="customer_id";
	private static final String AVG_RATINGS="avgRatings";
	private static final String DEAL_ID="deal_id";
	
    @Override
    public Map<Integer, Double> getRestrntsBookedByCust(int userId) {
        Map<Integer, Double> restrntsMap = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantBookingsFrCust(?)");
            storedProcedure.setParameter(1, userId);
            List<Map<String, Object>> userTableLst = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> row : userTableLst) {
                int restaurantId = (Integer) row.get(RESTAURANT_ID);
                Long count = Long.valueOf(row.get(BOOKING_COUNT).toString());
                Double bookingCount=count.doubleValue()	;
                restrntsMap.put(restaurantId, bookingCount);
            }
        } catch (SQLException e) {
        	logger.error("SQL error while fetching restaurants booked by customer",e);
        }
        return restrntsMap;
    }

    @Override
    public Map<Integer, Map<Integer, Double>> getUsersFrRestrnts(String restaurantIds) {
        Map<Integer, Map<Integer, Double>> otherUsersMap = new HashMap<>();

        try {
            IStoredProcedure storedProcedure1 = new StoredProcedure("getUsrsFrRestaurants(?)");
            storedProcedure1.setParameter(1, restaurantIds);
            List<Map<String, Object>> restAndUsrLst = storedProcedure1.executeAndFetchTable();
            for (Map<String, Object> row : restAndUsrLst) {
                int customerId = (Integer) row.get(CUSTOMER_ID);
                int restaurantId = (Integer) row.get(RESTAURANT_ID);
                Long count = Long.valueOf(row.get(BOOKING_COUNT).toString());
                Double bookingCount=count.doubleValue()	;
                if (otherUsersMap.containsKey(customerId)) {
                    Map<Integer, Double> resturantMappings = otherUsersMap.get(customerId);
                    resturantMappings.put(restaurantId, bookingCount);
                    otherUsersMap.put(customerId, resturantMappings);
                } else {
                    Map<Integer, Double> resturantMappings = new HashMap<>();
                    resturantMappings.put(restaurantId, bookingCount);
                    otherUsersMap.put(customerId, resturantMappings);
                }
            }
        } catch (SQLException e) {
        	logger.error("SQL error while fetching users for restaurants",e);
        } catch (Exception e) {
        	logger.error("Exception occurred while fetching");
        }
        return otherUsersMap;
    }

    @Override
    public Map<Integer, Double> getRestaurantsRatedByUsr(Integer similarUser, Double maxRatingsValue) {
        Map<Integer, Double> restrntsMap = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantsRatedByUsr(?,?)");
            storedProcedure.setParameter(1, similarUser);
            storedProcedure.setParameter(2, maxRatingsValue);
            List<Map<String, Object>> userTableLst = storedProcedure.executeAndFetchTable();
            getRatedRestaurants(restrntsMap, userTableLst);
        } catch (SQLException e) {
        	logger.error("SQL error while fetching restaurants rated by user",e);
        }

        return restrntsMap;
    }

    @Override
    public Map<Integer, Double> getHighestRatedRestrntFrUsr(int userId) {
        Map<Integer, Double> restrntsMap = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getHighestRatedRestrntFrUsr(?)");
            storedProcedure.setParameter(1, userId);
            List<Map<String, Object>> userTableLst = storedProcedure.executeAndFetchTable();
            getRatedRestaurants(restrntsMap, userTableLst);
        } catch (SQLException e) {
        	logger.error("SQL error while fetching highest rated restaurants customer",e);
        		
        }
        return restrntsMap;
    }

    @Override
    public int applyDeal(String dealId, String restaurantId, String customerId)  {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("applyDealById(?,?,?)");
            storedProcedure.setParameter(1, dealId);
            storedProcedure.setParameter(2, restaurantId);
            storedProcedure.setParameter(3, customerId);
            row = storedProcedure.executeWithResult();
        } catch (SQLException ex) {
        	logger.error("SQL error while applying deals",ex);
        }
        return row;
    }

    @Override
    public List<String> viewDealsByUser(String customerId)  {
        List<String> dealIds = new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("loadDealsByUser(?)");
            storedProcedure.setParameter(1, customerId);
            List<Map<String, Object>> dealTable = storedProcedure.executeAndFetchTable();
            dealIds = fetchDealIds(dealTable);
        } catch (SQLException e) {
        	logger.error("SQL error while loading deals for user",e);
        }
        return dealIds;
    }


    private void getRatedRestaurants(Map<Integer, Double> restrntsMap, List<Map<String, Object>> userTableLst) {
        for (Map<String, Object> row : userTableLst) {
            int restaurantId = (Integer) row.get(RESTAURANT_ID);
            double ratings = (Double) row.get(AVG_RATINGS);
            restrntsMap.put(restaurantId, ratings);
        }
    }

    private List<String> fetchDealIds(List<Map<String, Object>> dealTable) {
        List<String> dealIds = new ArrayList<>();
        for (Map<String, Object> row : dealTable) {
            dealIds.add(row.get(DEAL_ID).toString());
        }
        return dealIds;
    }

    @Override
    public int loadBalanceCustomerWallet (int customerId) {
        int amount = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("loadBalanceWalletAmountByUser(?)");
            storedProcedure.setParameter(1, customerId);
            Map<String, Object> customerWallet = storedProcedure.getTableRecord();
            amount = Integer.parseInt(customerWallet.get("wallet_amount").toString());
        }
        catch (SQLException e) {
            logger.error("SQL error while loading wallet amount for user",e);
        }
        return amount;
    }

    @Override
    public List<Integer> loadCouponCustomerWallet (int customerId) {
        List<Integer> amount = new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("loadCouponWalletAmountByUser(?)");
            storedProcedure.setParameter(1, customerId);
            List<Map<String, Object>> couponWallet = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> row : couponWallet) {
                amount.add(Integer.parseInt(row.get("wallet_amount").toString()));
            }
        }
        catch (SQLException e) {
            logger.error("SQL error while loading coupon wallet amount for user",e);
        }
        return amount;
    }
}

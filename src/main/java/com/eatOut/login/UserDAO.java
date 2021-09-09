package com.eatOut.login;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.List;
import java.util.Map;

public class UserDAO implements IUserDAO {
    User user;

    public UserDAO() {
        user = new User();
    }

    @Override
    public User getCustomerUserDetailsDB(String customerEmail) throws Exception {
        IStoredProcedure storedProcedure = new StoredProcedure("getCustomerDetailsByLogin(?)");
        return getCustomerInfo(storedProcedure, customerEmail);
    }

    @Override
    public User getRestaurantUserDetailsDB(String restaurantEmail) throws Exception {
        IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantDetailsByLogin(?)");
        return getRestaurantInfo(storedProcedure, restaurantEmail);
    }

    @Override
    public User getAdminUserDetailsDB(String adminEmail) throws Exception {
        IStoredProcedure storedProcedure = new StoredProcedure("getAdminLoginCredentials(?)");
        return getAdminInfo(storedProcedure, adminEmail);
    }

    private User getRestaurantInfo(IStoredProcedure storedProcedure, String restaurantEmail) throws Exception {
        try {
            storedProcedure.setParameter(1, restaurantEmail);
            List<Map<String, Object>> recordTable = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> record : recordTable) {
                user.setUserId(record.get("restaurant_id").toString());
                user.setPassword(record.get("password").toString());
                user.setUserName(record.get("restaurant_name").toString());
                user.setCity(record.get("city").toString());
                user.setCountry(record.get("country").toString());
            }
        } catch (Exception ex) {
            throw new Exception("Restaurant information not found");
        }
        return user;
    }

    private User getCustomerInfo(IStoredProcedure storedProcedure, String customerEmail) throws Exception {
        try {
            storedProcedure.setParameter(1, customerEmail);
            List<Map<String, Object>> recordTable = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> record : recordTable) {
                user.setUserId(record.get("customer_id").toString());
                user.setPassword(record.get("password").toString());
                user.setUserName(record.get("first_name").toString() + " - " + record.get("last_name").toString());
                user.setCity(record.get("city").toString());
                user.setCountry(record.get("country").toString());
            }
        } catch (Exception ex) {
            throw new Exception("Customer information not found");
        }
        return user;
    }

    private User getAdminInfo(IStoredProcedure storedProcedure, String AdminEmail) throws Exception {
        try {
            storedProcedure.setParameter(1, AdminEmail);
            Map<String, Object> record = storedProcedure.getTableRecord();
            user.setPassword(record.get("password").toString());
        } catch (Exception ex) {
            throw new Exception("Admin information not found");
        }
        return user;
    }
}

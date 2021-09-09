package com.eatOut.login;

public interface IUserDAO {
    User getAdminUserDetailsDB(String userLoginId) throws Exception;
    User getCustomerUserDetailsDB(String userLoginId) throws Exception;
    User getRestaurantUserDetailsDB(String userLoginId) throws Exception;
}


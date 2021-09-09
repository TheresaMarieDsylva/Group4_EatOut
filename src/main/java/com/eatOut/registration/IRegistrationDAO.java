package com.eatOut.registration;

public interface IRegistrationDAO {
    Boolean checkAdminDBIfUserExists(String email);
    Boolean createAdminDBUser(String email, String adminType, String phoneNum, String password, String city, String country, String dateTime);
    Boolean checkRestaurantDBIfUserExists(String email) throws Exception;
    Boolean createRestaurantUserInDB(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password, String restaurantStatus, String dateTime) throws Exception;
    Boolean checkCustomerDBIfUserExists(String email) throws Exception;
    Boolean createCustomerUserInDB(String firstName, String lastName, String phoneNumber, String city, String country, String email, String password, String dateTime) throws Exception;
}

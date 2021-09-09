package com.eatOut.registration;

public interface IAuthenticateRegistration {
    AuthenticateRegistration.RegisterResponse authenticateAdminRegistration(String email, String password, String confirmPassword, String adminType, String phoneNum, String city, String country);
    Boolean checkPasswordMatch(String password, String confirmPassword);
    Boolean checkIfAdminUserExists(String email, String adminType, String phoneNum, String password, String city, String country, String dateTime);
    void setFieldValues();
    Boolean checkIfRestaurantUserExists(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password) throws Exception;
    AuthenticateRegistration.RegisterResponse authenticateRestaurantRegistration(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password, String confirmPassword) throws Exception;
    Boolean checkIfCustomerUserExists(String firstName, String lastName, String phoneNumber, String city, String country, String email, String password) throws Exception;
    AuthenticateRegistration.RegisterResponse authenticateCustomerRegistration(String firstName, String lastName, String phoneNumber, String city, String country, String email, String password, String confirmPassword) throws Exception;
}

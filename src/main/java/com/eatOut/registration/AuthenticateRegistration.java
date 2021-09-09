package com.eatOut.registration;

import static com.eatOut.registration.AuthenticateRegistration.RegisterResponse.PASSWORD_DIDNT_MATCH;
import static com.eatOut.registration.AuthenticateRegistration.RegisterResponse.USER_ALREADY_EXISTS;
import static com.eatOut.registration.AuthenticateRegistration.RegisterResponse.USER_CREATED;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;

import com.eatOut.encryptionutils.AesCipher;
import com.eatOut.encryptionutils.IEatOutEncryption;
import com.eatOut.notifier.Status;

public class AuthenticateRegistration implements IAuthenticateRegistration {
    IRegistrationDAO iregisterDAO = new RegistrationDAO();
    IEatOutEncryption iEatOutEncryption = AesCipher.instance();
    public String dateTime= null;
    String restaurantStatus ="";
    RegisterResponse registerResponse;

    public enum RegisterResponse {
        USER_ALREADY_EXISTS,
        PASSWORD_DIDNT_MATCH,
        USER_CREATED
    }

    @Override
    public RegisterResponse authenticateAdminRegistration(String email, String password, String confirmPassword, String adminType, String phoneNum, String city, String country) {
        Boolean result = false;
        result = checkPasswordMatch(password,confirmPassword);
        if(result){
            setFieldValues();
            result = checkIfAdminUserExists(email,adminType, phoneNum,password,city,country,dateTime);
            if(result){
                registerResponse = USER_ALREADY_EXISTS;
            }
            else{
                registerResponse = USER_CREATED;
            }
        }
        else{
            registerResponse = PASSWORD_DIDNT_MATCH;
        }
        return registerResponse;
    }

    @Override
    public Boolean checkPasswordMatch(String password, String confirmPassword) {
        Boolean value=false;
        if(password.equals(confirmPassword)){
            value = true;
        }
        return value;
    }

    @Override
    public void setFieldValues() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        dateTime=formatter.format(date);
    }

    @Override
    public Boolean checkIfAdminUserExists(String email, String adminType, String phoneNum, String password, String city, String country, String dateTime){
        Boolean value = false;
        value = iregisterDAO.checkAdminDBIfUserExists(email);
        if(value.equals(false)){
            String encryptedKey = Hex.encodeHexString("rob".getBytes());
            try {
                password = iEatOutEncryption.encrypt(password,encryptedKey);
                value = iregisterDAO.createAdminDBUser(email,adminType, phoneNum,password,city,country,dateTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return value;
    }

    @Override
    public Boolean checkIfRestaurantUserExists(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password) throws Exception {
        Boolean checkIfRestaurantExists = false;
        try {
            checkIfRestaurantExists = iregisterDAO.checkRestaurantDBIfUserExists(email);
            if (checkIfRestaurantExists) {
                this.restaurantStatus = Status.WAITING.name();
                checkIfRestaurantExists = iregisterDAO.createRestaurantUserInDB(restaurantName, phoneNumber, fullAddress, city, country, email, password, restaurantStatus, dateTime);
            }
        }catch (Exception e) {
                throw new Exception("Exception occurred while checking existence of restaurant user");
            }
        return checkIfRestaurantExists;
    }

    @Override
    public RegisterResponse authenticateRestaurantRegistration(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password, String confirmPassword) throws Exception {
        Boolean authenticateRestaurant = false;
        try{
            authenticateRestaurant = checkPasswordMatch(password,confirmPassword);
            if(authenticateRestaurant){
                setFieldValues();
                authenticateRestaurant = checkIfRestaurantUserExists(restaurantName,phoneNumber,fullAddress,city,country,email,password);
                if(authenticateRestaurant){
                    registerResponse = USER_CREATED;
                } else{
                        registerResponse = USER_ALREADY_EXISTS;
                }
            } else{
                registerResponse = PASSWORD_DIDNT_MATCH;
            }
        } catch (Exception e) {
            throw new Exception("Exception occurred while authenticating restaurant user");
        }
        return registerResponse;
    }

    @Override
    public Boolean checkIfCustomerUserExists(String firstName, String lastName, String phoneNumber, String city, String country, String email, String password) throws Exception {
        Boolean checkIfCustomerExists = false;
        try {
            checkIfCustomerExists = iregisterDAO.checkCustomerDBIfUserExists(email);
            if (checkIfCustomerExists) {
                checkIfCustomerExists = iregisterDAO.createCustomerUserInDB(firstName, lastName, phoneNumber, city, country, email, password, dateTime);
            }
        } catch (Exception e) {
            throw new Exception("Exception occurred while checking existence of customer user");
        }
            return checkIfCustomerExists;
        }

        @Override
        public RegisterResponse authenticateCustomerRegistration (String firstName, String lastName, String phoneNumber, String city, String country, String email, String password, String confirmPassword) throws Exception {
            Boolean autheticateCustomer = false;
            try {
                autheticateCustomer = checkPasswordMatch(password, confirmPassword);
                if (autheticateCustomer) {
                    setFieldValues();
                    autheticateCustomer = checkIfCustomerUserExists(firstName, lastName, phoneNumber, city, country, email, password);
                    if (autheticateCustomer) {
                        registerResponse = USER_CREATED;
                    } else {
                        registerResponse = USER_ALREADY_EXISTS;
                    }
                } else {
                    registerResponse = PASSWORD_DIDNT_MATCH;
                }
            } catch (Exception ex) {
                throw new Exception("Exception occurred while authenticating customer user");
            }
            return registerResponse;
        }
    }

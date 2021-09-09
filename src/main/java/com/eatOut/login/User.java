package com.eatOut.login;

import com.eatOut.encryptionutils.AesCipher;
import com.eatOut.encryptionutils.IEatOutEncryption;
import org.apache.commons.codec.binary.Hex;

public class User implements IUser {
    private String loginId;
    private String password;
    private String userId;
    private String userName;
    private String city;
    private String country;
    private String loginResponse;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(String loginResponse) {
        this.loginResponse = loginResponse;
    }

    public User authenticate(IUserDAO authDAO, String loginId, String password, UserType userType) throws Exception {
        User user = new User();
        try {
            user = getUserDetailsDB(authDAO, userType, loginId);
            IEatOutEncryption encryption = AesCipher.instance();
            String encryptedKey = Hex.encodeHexString("rob".getBytes());
            String decryptedValue = encryption.decrypt(user.getPassword(), encryptedKey);
            boolean isPasswordValid = decryptedValue.equals(password);
            String userResp = isPasswordValid ? LoginResponse.SUCCESS.toString() : LoginResponse.FAILURE.toString();
            user.setLoginResponse(userResp);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return user;
    }

    private User getUserDetailsDB(IUserDAO authDAO, UserType userType, String userId) throws Exception {
        User user = null;
        try {
            if (userType.equals(UserType.ADMIN)) {
                user = authDAO.getAdminUserDetailsDB(userId);
            } else if (userType.equals(UserType.CUSTOMER)) {
                user = authDAO.getCustomerUserDetailsDB(userId);
            } else if (userType.equals(UserType.RESTAURANT)) {
                user = authDAO.getRestaurantUserDetailsDB(userId);
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
        return user;
    }
}

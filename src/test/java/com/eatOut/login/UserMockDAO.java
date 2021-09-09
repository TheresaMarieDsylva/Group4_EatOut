package com.eatOut.login;

public class UserMockDAO implements IUserDAO {
    User user;

    public UserMockDAO() {
        user = new User();
    }

    @Override
    public User getCustomerUserDetailsDB(String userId) throws Exception {
        try {
            if (userId.equals("rob@gmail.com")) {
                user.setUserId("3");
                user.setUserName("Rob - Hawkey");
                user.setPassword("5308");
                user.setCity("Halifax");
                user.setCountry("Canada");
                return user;
            }
        } catch (Exception ex) {
            throw new Exception("Customer information does not exist");
        }
        return null;
    }

    @Override
    public User getRestaurantUserDetailsDB(String userId) throws Exception {
        try {
            if (userId.equals("seasmoke@eatout.com")) {
                user.setUserId("3");
                user.setUserName("SeaSmoke");
                user.setPassword("1234");
                user.setCity("Halifax");
                user.setCountry("Canada");
                return user;
            }
        } catch (Exception ex) {
            throw new Exception("Restaurant information does not exist");
        }
        return null;
    }

    @Override
    public User getAdminUserDetailsDB(String userId) throws Exception {
        try {
            if (userId.equals("ann@eatout.com")) {
                user.setPassword("5444");
                return user;
            }
        } catch (Exception ex) {
            throw new Exception("Admin information does not exist");
        }
        return null;
    }
}
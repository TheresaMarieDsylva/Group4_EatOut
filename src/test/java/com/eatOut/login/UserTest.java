package com.eatOut.login;

import org.junit.*;

public class UserTest {
    UserAbstractFactoryTest userAbstractFactoryTest;
    IUser user;
    IUserDAO userDAO;

    @Before()
    public void init() {
        userAbstractFactoryTest = new UserConcreteFactoryTest();
        user = userAbstractFactoryTest.getUser();
        userDAO = userAbstractFactoryTest.getUserDAO();
    }

    @Test
    public void authenticate_customer_with_empty_email() throws Exception {
        User userInfo = user.authenticate(new UserMockDAO(), "", "1234", UserType.CUSTOMER);
        Assert.assertNull(userInfo);
    }

    @Test
    public void authenticate_admin_unregistered_email() throws Exception {
        User userInfo = user.authenticate(new UserMockDAO(), "abc@gmail.com", "1234", UserType.ADMIN);
        Assert.assertNull(userInfo);
    }

    @Test
    public void authenticate_restaurant_with_valid_email_valid_password() throws Exception {
        User userInfo = user.authenticate(new UserMockDAO(), "seasmoke@eatout.com", "1234", UserType.RESTAURANT);
        Assert.assertEquals(userInfo.getLoginResponse(), LoginResponse.SUCCESS.name());
    }

    @Test
    public void authenticate_admin_with_invalid_email_valid_password() throws Exception {
        User userInfo =  user.authenticate(new UserMockDAO(), "ann@gmail.com", "5444", UserType.ADMIN);
        Assert.assertNull(userInfo);
    }

    @Test
    public void authenticate_customer_with_valid_email_valid_password() throws Exception {
        User userInfo = user.authenticate(new UserMockDAO(), "rob@gmail.com", "5308", UserType.CUSTOMER);
        Assert.assertEquals(userInfo.getLoginResponse(), LoginResponse.SUCCESS.name());
    }

    @Test
    public void authenticate_restaurant_with_invalid_email_invalid_password() throws Exception {
        User userInfo = user.authenticate(new UserMockDAO(), "sea@gmail.com", "4543", UserType.RESTAURANT);
        Assert.assertNull(userInfo);
    }
}

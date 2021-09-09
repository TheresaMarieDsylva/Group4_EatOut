package com.eatOut.registration;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticateRegistrationTest {
    static IAuthenticateRegistration iauthenticateRegistration =null;
    static IRegistrationDAO iRegistrationDAO = null;
    static AuthenticateRegistration authenticateRegistration =null;

    @BeforeClass
    public static void setUp() {
       iauthenticateRegistration = new AuthenticateRegistration();
        iRegistrationDAO = mock(RegistrationDAO.class);
        authenticateRegistration = new AuthenticateRegistration();
    }

    @Test
    public void checkPasswordMatchTest(){
        String password = "userWorld";
        String rpassword = "userWorld";
        Boolean value = iauthenticateRegistration.checkPasswordMatch(password,rpassword);
        Assert.assertEquals(true, value);
        Assert.assertNotEquals(false,value);
    }

    @Test
    public void checkPasswordMisMatchTest(){
        String password = "userWorld";
        String rpassword = "user";
        Boolean value = iauthenticateRegistration.checkPasswordMatch(password,rpassword);
        Assert.assertEquals(false, value);
        Assert.assertNotEquals(true,value);
    }

    @Test
    public void setFieldValuesTest(){
        authenticateRegistration.setFieldValues();
        String dateTime=authenticateRegistration.dateTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        String dateTime1=(String)formatter.format(date);

        Assert.assertEquals(dateTime,dateTime1);
    }

    @Test
    public void checkDBIfAdminUserExistsTest(){
        when(iRegistrationDAO.checkAdminDBIfUserExists("humble@dal.ca")).thenReturn(true);
        Assert.assertEquals(true, iRegistrationDAO.checkAdminDBIfUserExists("humble@dal.ca"));
        when(iRegistrationDAO.checkAdminDBIfUserExists("joey@dal.ca")).thenReturn(false);
        Assert.assertNotEquals(true, iRegistrationDAO.checkAdminDBIfUserExists("joey@dal.ca"));

    }

    @Test
    public void createAdminDBUserTest(){
        when(iRegistrationDAO.createAdminDBUser("humble@dal.ca","admin","9089738432","1234","toronto", "canada","12/03/2021 18:03:12")).thenReturn(true);
        Assert.assertNotEquals(false, iRegistrationDAO.createAdminDBUser("humble@dal.ca", "admin", "9089738432", "1234", "toronto", "canada","12/03/2021 18:03:12"));
        Assert.assertEquals(true, iRegistrationDAO.createAdminDBUser("humble@dal.ca", "admin", "9089738432", "1234", "toronto", "canada","12/03/2021 18:03:12"));
    }

    @Test
    public void checkIfCustomerUserExists(){
        try {
            when(iRegistrationDAO.checkCustomerDBIfUserExists("andrew@gmail.com")).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(true, iRegistrationDAO.checkCustomerDBIfUserExists("andrew@gmail.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            when(iRegistrationDAO.checkCustomerDBIfUserExists("phoebe@dal.ca")).thenReturn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertNotEquals(true, iRegistrationDAO.checkCustomerDBIfUserExists("phoebe@dal.ca"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCustomerDBUserTest(){
        try {
            when(iRegistrationDAO.createCustomerUserInDB("humble","bay","9089738432","halifax","toronto", "humble@dal.ca","1234","12/03/2021 18:03:12")).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertNotEquals(false, iRegistrationDAO.createCustomerUserInDB("humble","bay","9089738432","halifax","toronto", "humble@dal.ca","1234","12/03/2021 18:03:12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(true, iRegistrationDAO.createCustomerUserInDB("humble","bay","9089738432","halifax","toronto", "humble@dal.ca","1234","12/03/2021 18:03:12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRestaurantUserExists(){
        try {
            when(iRegistrationDAO.checkRestaurantDBIfUserExists("andrew@dal.ca")).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(true, iRegistrationDAO.checkRestaurantDBIfUserExists("andrew@dal.ca"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            when(iRegistrationDAO.checkRestaurantDBIfUserExists("phoebe@dal.ca")).thenReturn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertNotEquals(true, iRegistrationDAO.checkRestaurantDBIfUserExists("phoebe@dal.ca"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createRestaurantDBUserTest(){
        try {
            when(iRegistrationDAO.createRestaurantUserInDB("Chilis","12345558881","30, Bagby Point,St Andrew Park","halifax","toronto", "humble@dal.ca","1234","WAITING","12/03/2021 18:03:12")).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertNotEquals(false, iRegistrationDAO.createRestaurantUserInDB("Chilis","12345558881","30, Bagby Point,St Andrew Park","halifax","toronto", "humble@dal.ca","1234","WAITING","12/03/2021 18:03:12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(true, iRegistrationDAO.createRestaurantUserInDB("Chilis","12345558881","30, Bagby Point,St Andrew Park","halifax","toronto", "humble@dal.ca","1234","WAITING","12/03/2021 18:03:12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        iauthenticateRegistration = null;
        iRegistrationDAO = null;
        authenticateRegistration = null;
    }
}

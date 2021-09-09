package com.eatOut.profile;

import com.eatOut.customerHomepage.CustomerMembership;
import com.eatOut.menu.IMenu;
import com.eatOut.menu.IMenuDAO;
import com.eatOut.menu.MenuAbstractFactory;
import com.eatOut.menu.MenuConcreteFactoryTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileTest {

    ProfileAbstractFactory profileAbstractFactory;
    IProfile profile;
    IProfileDAO profileDAO;

    @Before()
    public void init() {
        profileAbstractFactory=new ProfileConcreteFactoryTest();
        profile=profileAbstractFactory.getProfile();
        profileDAO=profileAbstractFactory.getProfileDao();
    }

    @Test
    public void showUsedCouponsDB(){
        when(profileDAO.showUsedCouponsByCustomerDB(1)).thenReturn(new ArrayList<Profile>());
        assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showUsedCouponsByCustomerDB(1));
    }

    @Test
    public void showBookingHistoryDB(){
        when(profileDAO.showBookingHistoryForCustomerDB(1)).thenReturn(new ArrayList<Profile>());
        assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showBookingHistoryForCustomerDB(1));
    }

    @Test
    public void showRestaurantBookingHistoryDB() throws Exception{
            when(profileDAO.showBookingHistoryForRestaurantDB(1)).thenReturn(new ArrayList<Profile>());
            assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showBookingHistoryForRestaurantDB(1));
    }

    @Test
    public void showOrderHistoryDB(){
        when(profileDAO.showOrderHistoryForCustomerDB(1)).thenReturn(new ArrayList<Profile>());
        assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showOrderHistoryForCustomerDB(1));
    }

    @Test
    public void showRestaurantOrderHistoryDB() throws Exception{
            when(profileDAO.showRestaurantOrderHistoryForRestaurantDB(1)).thenReturn(new ArrayList<Profile>());
            assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showRestaurantOrderHistoryForRestaurantDB(1));
    }

    @Test
    public void showUserReviewsDB(){
        when(profileDAO.showUserReviewsDB(1)).thenReturn(new ArrayList<Profile>());
        assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showUserReviewsDB(1));
    }

    @Test
    public void showRestaurantReviewsDB() throws Exception{
        when(profileDAO.showReviewsForRestaurantDB(1)).thenReturn(new ArrayList<Profile>());
        assertEquals(new ArrayList<CustomerMembership>(), profileDAO.showReviewsForRestaurantDB(1));
    }

}

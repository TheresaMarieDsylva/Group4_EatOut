package com.eatOut.customerhomepage;

import com.eatOut.customerHomepage.CustomerMembership;
import com.eatOut.customerHomepage.CustomerMembershipDAO;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerMembershipTest {

    static CustomerMembershipDAO customerMembershipDAO = mock(CustomerMembershipDAO.class);

    @Test
    public void addCustomerToMembership(){
        when(customerMembershipDAO.addCustomerToMembership(1,"SaverPro")).thenReturn(true);;
        assertEquals(true, customerMembershipDAO.addCustomerToMembership(1,"SaverPro"));
        when(customerMembershipDAO.addCustomerToMembership(1,"SaverPro")).thenReturn(false);;
        assertEquals(false, customerMembershipDAO.addCustomerToMembership(1,"SaverPro"));

    }

    @Test
    public void displayMembershipDetails(){
        when(customerMembershipDAO.displayMembershipDetails()).thenReturn(new ArrayList<CustomerMembership>());;
        assertEquals(new ArrayList<CustomerMembership>(), customerMembershipDAO.displayMembershipDetails());

    }

    @AfterClass
    public static void tearDown() {
        customerMembershipDAO = null;

    }
}

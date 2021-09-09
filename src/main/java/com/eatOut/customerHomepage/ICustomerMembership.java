package com.eatOut.customerHomepage;

import java.util.List;

public interface ICustomerMembership {
    List<CustomerMembership> displayMembership();

    Boolean addCustomerMembership(int tempCusID, String membershipName);
}

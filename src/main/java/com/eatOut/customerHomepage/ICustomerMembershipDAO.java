package com.eatOut.customerHomepage;

import java.util.List;

public interface ICustomerMembershipDAO {

    Boolean addCustomerToMembership(int tempCusID, String membershipName);

    List<CustomerMembership> displayMembershipDetails();
}

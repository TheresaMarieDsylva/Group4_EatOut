package com.eatOut.membership;

import java.util.List;

public interface IMembership {
    List<Membership> loadMembership(IMembershipDAO membershipDAO) throws Exception;
    int addMembership(IMembershipDAO membershipDAO, String membershipName, int dining, int takeaway) throws Exception;
    int disableMembership(IMembershipDAO membershipDAO, String[] membershipName) throws Exception;
}

package com.eatOut.membership;

import java.util.List;
import java.util.Map;

public interface IMembershipDAO {
    public List<Map<String, Object>> loadMembership() throws Exception;
    public int addMembershipCard(String membershipName, int dining, int takeaway) throws Exception;
    public int disableMembershipCard(String[] membershipName) throws Exception;

}

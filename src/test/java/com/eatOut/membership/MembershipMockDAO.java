package com.eatOut.membership;

import java.util.*;

public class MembershipMockDAO implements IMembershipDAO {

    @Override
    public List<Map<String, Object>> loadMembership() {
        List<Map<String, Object>> memberships = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("membership_name", "Pro");
        map.put("dining_percent", 3);
        map.put("takeaway_percent", 5);
        map.put("membership_status", "ACTIVE");
        map.put("created_at", new java.util.Date());
        memberships.add(map);
        return memberships;
    }

    @Override
    public int addMembershipCard(String membershipName, int dining, int takeaway) {
        if (!membershipName.isEmpty()) {
            return 1;
        }
        return -1;
    }

    @Override
    public int disableMembershipCard(String[] membershipNames) {
        for(String membership: membershipNames) {
            if (membership.equals("Pro")) {
                return 1;
            }
        }
        return -1;
    }
}

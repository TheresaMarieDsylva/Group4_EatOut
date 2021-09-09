package com.eatOut.membership;

import com.eatOut.notifier.Status;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.List;
import java.util.Map;

public class MembershipDAO implements IMembershipDAO {

    public List<Map<String, Object>> loadMembership() throws Exception {
        IStoredProcedure storedProcedure = new StoredProcedure("getActiveMemberships(?)");
        storedProcedure.setParameter(1, Status.ACTIVE.toString());
        List<Map<String, Object>> memberships = storedProcedure.executeAndFetchTable();
        return memberships;
    }

    public int addMembershipCard(String membershipName, int dining, int takeaway) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertMembership(?,?,?,?)");
            storedProcedure.setParameter(1, membershipName);
            storedProcedure.setParameter(2, dining);
            storedProcedure.setParameter(3, takeaway);
            storedProcedure.setParameter(4, Status.ACTIVE.toString());
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Unable to add membership " + membershipName);
        }
        return row;
    }

    @Override
    public int disableMembershipCard(String[] membershipNames) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("disableMembership(?,?)");
            for (String membershipName : membershipNames) {
                storedProcedure.setParameter(1, membershipName);
                storedProcedure.setParameter(2, Status.INACTIVE.toString());
                row += storedProcedure.executeWithResult();
            }
        }
        catch (Exception ex) {
            throw new Exception("Not able to disable the membership");
        }
        return row;
    }
}

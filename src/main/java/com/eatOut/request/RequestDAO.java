package com.eatOut.request;

import com.eatOut.notifier.Status;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

public class RequestDAO implements IRequestDAO {

    @Override
    public int approveRequestItemsByIds(String[] approveIds) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("approveRequestItemsById(?,?)");
            for (String approveId : approveIds) {
                storedProcedure.setParameter(1, Status.APPROVED.toString());
                storedProcedure.setParameter(2, approveId);
                row += storedProcedure.executeWithResult();
            }
        } catch (Exception ex) {
            throw new Exception("Unable to approve restaurant request ");
        }
        return row;
    }

    @Override
    public int rejectRequestItemsByIds(String[] rejectIds) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("rejectRequestItemsById(?,?)");
            for (String rejectId : rejectIds) {
                storedProcedure.setParameter(1, Status.REJECTED.toString());
                storedProcedure.setParameter(2, rejectId);
                row += storedProcedure.executeWithResult();
            }
        } catch (Exception ex) {
            throw new Exception("Unable to reject restaurant requests ");
        }
        return row;
    }
}

package com.eatOut.request;

public class Request implements IRequest {

    @Override
    public int approveRequestItemsByIds(IRequestDAO requestDAO, String approveIds) throws Exception {
        String[] approveArr = approveIds.split(",");
        return requestDAO.approveRequestItemsByIds(approveArr);
    }

    @Override
    public int rejectRequestItemsByIds(IRequestDAO requestDAO, String rejectIds) throws Exception {
        String[] rejectArr = rejectIds.split(",");
        return requestDAO.rejectRequestItemsByIds(rejectArr);
    }
}

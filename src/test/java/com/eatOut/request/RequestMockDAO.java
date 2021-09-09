package com.eatOut.request;

public class RequestMockDAO implements IRequestDAO {
    @Override
    public int approveRequestItemsByIds(String[] approveIds) {
        return 1;
    }

    @Override
    public int rejectRequestItemsByIds(String[] rejectIds) {
        return 0;
    }
}

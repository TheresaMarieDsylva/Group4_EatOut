package com.eatOut.request;

public interface IRequestDAO {
    int approveRequestItemsByIds(String[] approveIds) throws Exception;
    int rejectRequestItemsByIds(String[] rejectIds) throws Exception;
}
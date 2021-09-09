package com.eatOut.request;

public interface IRequest {
    int approveRequestItemsByIds(IRequestDAO requestDAO, String approveIds) throws Exception;
    int rejectRequestItemsByIds(IRequestDAO requestDAO, String rejectIds) throws Exception;
}

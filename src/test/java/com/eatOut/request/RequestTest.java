package com.eatOut.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {
    RequestAbstractFactoryTest requestAbstractFactoryTest;
    IRequest request;
    IRequestDAO requestDAO;

    @Before()
    public void init() {
        requestAbstractFactoryTest = new RequestConcreteFactoryTest();
        request = requestAbstractFactoryTest.getRequests();
        requestDAO = requestAbstractFactoryTest.getRequestDAO();
    }

    @Test
    public void approveRequestItemsByIds_valid_test() throws Exception{
        String approveArr = "12";
        Assert.assertEquals(request.approveRequestItemsByIds(requestDAO, approveArr), 1);
    }

    @Test
    public void rejectRequestItemsByIds_invalid_test() throws Exception{
        String rejectArr = "12";
        Assert.assertEquals(request.rejectRequestItemsByIds(requestDAO, rejectArr), 0);
    }
}

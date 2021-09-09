package com.eatOut.request;

public class RequestConcreteFactoryTest extends RequestAbstractFactoryTest {

    @Override
    public IRequest getRequests() {
        return new Request();
    }

    @Override
    public IRequestDAO getRequestDAO() {
        return new RequestMockDAO() ;
    }
}

package com.eatOut.request;

public class RequestConcreteFactory extends RequestAbstractFactory {

    @Override
    public IRequest getRequests() {
        return new Request();
    }

    @Override
    public IRequestDAO getRequestDAO() {
        return new RequestDAO();
    }
}

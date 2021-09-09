package com.eatOut.request;

public abstract class RequestAbstractFactory {
    public abstract IRequest getRequests();
    public abstract IRequestDAO getRequestDAO();
}

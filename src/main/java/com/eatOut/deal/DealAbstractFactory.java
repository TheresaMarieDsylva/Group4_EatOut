package com.eatOut.deal;

public abstract class DealAbstractFactory {

    public abstract IDealDAO getCustomerDealDAO();

    public abstract IDealOffer getCustomerDealOffers();
}

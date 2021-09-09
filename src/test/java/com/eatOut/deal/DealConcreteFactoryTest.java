package com.eatOut.deal;


public class DealConcreteFactoryTest extends DealAbstractFactory {

    @Override
    public IDealDAO getCustomerDealDAO() {
        return new DealMockDAO();
    }

    @Override
    public IDealOffer getCustomerDealOffers() {
        return new DealOffer();
    }
}
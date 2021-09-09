package com.eatOut.deal;

public class DealConcreteFactory extends DealAbstractFactory {

    @Override
    public IDealDAO getCustomerDealDAO() {
        return new DealDAO();
    }

    @Override
    public IDealOffer getCustomerDealOffers() {
        return new DealOffer();
    }
}

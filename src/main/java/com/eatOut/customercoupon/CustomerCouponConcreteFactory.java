package com.eatOut.customercoupon;

public class CustomerCouponConcreteFactory extends CustomerCouponAbstractFactory {

    @Override
    public ICustomerCoupon getCustomerCoupon() {
        return new CustomerCoupon();
    }

    @Override
    public ICustomerCouponDAO getCustomerCouponDAO() {
        return new CustomerCouponDAO();
    }
}

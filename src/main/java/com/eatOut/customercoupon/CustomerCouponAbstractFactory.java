package com.eatOut.customercoupon;

public abstract class CustomerCouponAbstractFactory {
    public abstract ICustomerCoupon getCustomerCoupon();
    public abstract ICustomerCouponDAO getCustomerCouponDAO();
}

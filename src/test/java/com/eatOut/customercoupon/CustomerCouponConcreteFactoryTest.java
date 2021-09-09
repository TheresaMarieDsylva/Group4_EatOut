package com.eatOut.customercoupon;

public class CustomerCouponConcreteFactoryTest extends CustomerCouponAbstractFactoryTest {
    @Override
    public ICustomerCoupon getCustomerCoupon() {
        return new CustomerCoupon();
    }

    @Override
    public ICustomerCouponDAO getCustomerCouponDAO() {
        return new CustomerCouponMockDAO();
    }
}

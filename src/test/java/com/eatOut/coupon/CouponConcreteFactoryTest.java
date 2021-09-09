package com.eatOut.coupon;

public class CouponConcreteFactoryTest extends CouponAbstractFactoryTest {
    @Override
    public ICouponGenerator getCouponGenerator() {
        return new CouponGenerator();
    }

    @Override
    public ICouponGeneratorDAO getCouponGeneratorDAO() {
        return new CouponGeneratorDAO();
    }
}

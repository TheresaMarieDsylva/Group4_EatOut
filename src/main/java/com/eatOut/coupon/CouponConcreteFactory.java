package com.eatOut.coupon;

public class CouponConcreteFactory extends CouponAbstractFactory {
    @Override
    public ICouponGenerator getCouponGenerator() {
        return new CouponGenerator();
    }

    @Override
    public ICouponGeneratorDAO getCouponGeneratorDAO() {
        return new CouponGeneratorDAO();
    }
}

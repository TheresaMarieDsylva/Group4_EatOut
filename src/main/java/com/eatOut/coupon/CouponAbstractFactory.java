package com.eatOut.coupon;

public abstract class CouponAbstractFactory {
    public abstract ICouponGenerator getCouponGenerator();
    public abstract ICouponGeneratorDAO getCouponGeneratorDAO();
}

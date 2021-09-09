package com.eatOut.coupon;

import java.util.List;

public interface ICouponGenerator {
    int addCoupons(ICouponGeneratorDAO couponGeneratorDAO, String name, String quantity, String amount, String description, String expiryDate) throws Exception;
    List<CouponGenerator> loadCoupons(ICouponGeneratorDAO couponGeneratorDAO) throws Exception;
    String generateCouponCode(String couponName);
}

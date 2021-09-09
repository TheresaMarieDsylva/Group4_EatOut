package com.eatOut.customercoupon;

import com.eatOut.coupon.CouponGenerator;

import java.util.List;

public interface ICustomerCouponDAO {
    List<CouponGenerator> loadCouponsByUserId(String customerId) throws Exception;
    int applyCoupons(int coupon, String name, String amount, String customerId) throws Exception;
}
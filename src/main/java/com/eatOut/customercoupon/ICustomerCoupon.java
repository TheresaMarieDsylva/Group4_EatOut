package com.eatOut.customercoupon;

import com.eatOut.coupon.CouponGenerator;
import com.eatOut.coupon.ICouponGeneratorDAO;

import java.util.List;

public interface ICustomerCoupon {
    List<CouponGenerator> loadValidCoupons(ICustomerCouponDAO customerCouponsDAO, ICouponGeneratorDAO couponGeneratorDAO, String customerId) throws Exception;
    int applyCoupons(ICustomerCouponDAO customerCouponsDAO, String couponId, String name, String amount, String customerId) throws Exception;
    List<CouponGenerator> filterCouponsByQtyAndDate(List<CouponGenerator> coupons, List<CouponGenerator> couponsByUser);
}

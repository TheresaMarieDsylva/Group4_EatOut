package com.eatOut.coupon;

import java.sql.Timestamp;
import java.util.List;

public interface ICouponGeneratorDAO {
    int insertCoupons(String name, String code, String quantity, String amount, String description, Timestamp expiryDate) throws Exception;
    List<CouponGenerator> viewCoupons() throws Exception;
}

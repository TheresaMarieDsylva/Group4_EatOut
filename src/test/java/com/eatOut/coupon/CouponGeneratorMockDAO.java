package com.eatOut.coupon;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CouponGeneratorMockDAO implements ICouponGeneratorDAO {
    @Override
    public int insertCoupons(String name, String code, String quantity, String amount, String description, Timestamp expiryDate) throws Exception {
        return 1;
    }

    @Override
    public List<CouponGenerator> viewCoupons() throws Exception {
        return new ArrayList<>();
    }
}

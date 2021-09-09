package com.eatOut.customercoupon;

import com.eatOut.coupon.CouponGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomerCouponMockDAO implements ICustomerCouponDAO {
    @Override
    public List<CouponGenerator> loadCouponsByUserId(String customerId) throws Exception {
        return new ArrayList<>();
    }

    @Override
    public int applyCoupons(int coupon, String name, String amount, String customerId) throws Exception {
        return 1;
    }
}

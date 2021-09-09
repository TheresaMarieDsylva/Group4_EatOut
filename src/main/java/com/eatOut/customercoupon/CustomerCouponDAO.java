package com.eatOut.customercoupon;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;
import com.eatOut.coupon.CouponGenerator;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerCouponDAO implements ICustomerCouponDAO {
    List<CouponGenerator> coupons;
    IDateCalculator dateCalculator;

    public CustomerCouponDAO() {
        coupons = new ArrayList<>();
        dateCalculator = new CalendarDateCalculator();
    }

    @Override
    public List<CouponGenerator> loadCouponsByUserId(String customerId) throws Exception {
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("loadCouponsByUser(?)");
            storedProcedure.setParameter(1, customerId);
            List<Map<String, Object>> couponTable = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> row : couponTable) {
                CouponGenerator coupon = new CouponGenerator();
                coupon.setCouponId(Integer.parseInt(row.get("coupon_id").toString()));
                coupon.setCouponName(row.get("coupon_name").toString());
                coupon.setCouponCode(row.get("coupon_code").toString());
                coupon.setQuantity(Integer.parseInt(row.get("quantity").toString()));
                coupon.setAmount(Integer.parseInt(row.get("amount").toString()));
                coupon.setDescription(row.get("description").toString());
                coupon.setExpiryDate(dateCalculator.parseToLocalDateTime(row.get("expiry_date").toString()));
                coupons.add(coupon);
            }
        } catch (Exception ex) {
            throw new Exception("Unable to load coupons " + ex.getMessage());
        }
        return coupons;
    }

    @Override
    public int applyCoupons(int coupon, String name, String amount, String customerId) throws Exception {
        int row = 0;
        try {
            StoredProcedure storedProcedure = new StoredProcedure("applyCouponById(?,?,?)");
            storedProcedure.setParameter(1, coupon);
            storedProcedure.setParameter(2, amount);
            storedProcedure.setParameter(3, customerId);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Unable to apply coupon " + name + ex.getMessage());
        }
        return row;
    }
}

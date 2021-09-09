package com.eatOut.coupon;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CouponGeneratorDAO implements ICouponGeneratorDAO {
    List<CouponGenerator> coupons;
    IDateCalculator dateCalculator;

    public CouponGeneratorDAO() {
        coupons = new ArrayList<>();
        dateCalculator = new CalendarDateCalculator();
    }

    @Override
    public int insertCoupons(String couponName, String couponCode, String quantity, String amount, String description, Timestamp expiryDate) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertCoupons(?,?,?,?,?,?)");
            storedProcedure.setParameter(1, couponName);
            storedProcedure.setParameter(2, couponCode);
            storedProcedure.setParameter(3, quantity);
            storedProcedure.setParameter(4, amount);
            storedProcedure.setParameter(5, description);
            storedProcedure.setParameter(6, expiryDate);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Unable to add coupons " + couponName + ex.getMessage());
        }
        return row;
    }

    @Override
    public List<CouponGenerator> viewCoupons() throws Exception {
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("loadAllCoupons()");
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
            throw new Exception("Unable to load coupons "+ex.getMessage());
        }
        return coupons;
    }
}

package com.eatOut.customercoupon;

import com.eatOut.coupon.CouponGenerator;
import com.eatOut.coupon.ICouponGeneratorDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerCoupon implements ICustomerCoupon {


    @Override
    public List<CouponGenerator> loadValidCoupons(ICustomerCouponDAO customerCouponsDAO, ICouponGeneratorDAO couponGeneratorDAO, String customerId) throws Exception {
        List<CouponGenerator> couponsByUser = customerCouponsDAO.loadCouponsByUserId(customerId);
        List<CouponGenerator> coupons = couponGeneratorDAO.viewCoupons();
        return filterCouponsByQtyAndDate(coupons, couponsByUser);
    }

    @Override
    public int applyCoupons(ICustomerCouponDAO customerCouponsDAO, String couponId, String name, String amount, String customerId) throws Exception {
        int coupon = Integer.parseInt(couponId);
        return customerCouponsDAO.applyCoupons(coupon, name, amount, customerId);
    }

    @Override
    public List<CouponGenerator> filterCouponsByQtyAndDate(List<CouponGenerator> coupons, List<CouponGenerator> couponsByUser) {
        if(couponsByUser.size() > 0) {
            coupons = coupons.stream()
                    .filter(coup -> couponsByUser.stream().map(CouponGenerator::getCouponId).noneMatch(id -> id.equals(coup.getCouponId())))
                    .collect(Collectors.toList());
        }

        return coupons.stream()
                .filter(coupon -> {
                    return coupon.getQuantity() > 0 &&
                            isDateExpired(coupon.getExpiryDate());
                })
                .collect(Collectors.toList());
    }

    public boolean isDateExpired(LocalDateTime expiryDate) {
        Timestamp currentDateTime = Timestamp.valueOf(java.time.LocalDateTime.now());
        Timestamp expiryDateTime = Timestamp.valueOf(expiryDate);
        return currentDateTime.before(expiryDateTime);
    }
}

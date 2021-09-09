package com.eatOut.customercoupon;

import com.eatOut.coupon.CouponAbstractFactoryTest;
import com.eatOut.coupon.CouponConcreteFactoryTest;
import com.eatOut.coupon.CouponGenerator;
import com.eatOut.coupon.ICouponGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerCouponTest {
    private static List<CouponGenerator> coupons;
    private static List<CouponGenerator> userCoupons;
    CustomerCouponAbstractFactoryTest customerCouponAbstractFactory;
    CouponAbstractFactoryTest couponAbstractFactoryTest;

    ICouponGenerator couponGenerator;
    ICustomerCoupon customerCoupon;
    ICustomerCouponDAO customerCouponDAO;
    CouponGenerator coupon;

    @Before()
    public void init() {
        customerCouponAbstractFactory = new CustomerCouponConcreteFactoryTest();
        couponAbstractFactoryTest = new CouponConcreteFactoryTest();
        customerCoupon = customerCouponAbstractFactory.getCustomerCoupon();
        customerCouponDAO = customerCouponAbstractFactory.getCustomerCouponDAO();
        couponGenerator = couponAbstractFactoryTest.getCouponGenerator();
        coupon = new CouponGenerator();
        coupons = new ArrayList<>();
        userCoupons = new ArrayList<>();
    }

    @Test
    public void filterCouponsByQtyAndDate_beforeExpiryDate_test() {
        coupon.setCouponId(1);
        coupon.setQuantity(5);
        coupon.setExpiryDate(LocalDateTime.now().plusDays(2));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        coupon.setQuantity(10);
        coupon.setExpiryDate(LocalDateTime.now().plusDays(2));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(3);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 2);
    }

    @Test
    public void filterCouponsByQtyAndDate_afterExpiryDate_test() {
        coupon.setCouponId(1);
        coupon.setQuantity(5);
        coupon.setExpiryDate(LocalDateTime.parse(("24-03-2021 15:44:45"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        coupon.setQuantity(10);
        coupon.setExpiryDate(LocalDateTime.parse(("25-03-2021 12:01:45"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(3);
        coupon.setQuantity(10);
        coupon.setExpiryDate(LocalDateTime.now().plusDays(2));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(1);
        userCoupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 1);
    }

    @Test
    public void filterCouponsByQtyAndDate_onExpiryDate_test() {
        coupon.setCouponId(2);
        coupon.setQuantity(5);
        coupon.setExpiryDate(LocalDateTime.parse(("25-03-2021 15:44:45"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 0);
    }

    @Test
    public void filterCouponsByQtyAndDate_onZeroQty_test() {
        coupon.setCouponId(3);
        coupon.setQuantity(0);
        coupon.setExpiryDate(LocalDateTime.parse(("26-03-2021 15:44:45"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 0);
    }

    @Test
    public void filterCouponsByQtyAndDate_onMoreQty_test() {
        coupon.setCouponId(3);
        coupon.setQuantity(100);
        coupon.setExpiryDate(LocalDateTime.now().plusDays(2));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 1);
    }

    @Test
    public void filterCouponsByQtyAndDate_onNegQty_test() {
        coupon.setCouponId(2);
        coupon.setQuantity(-1);
        coupon.setExpiryDate(LocalDateTime.parse(("26-03-2021 15:44:45"), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        coupons.add(coupon);
        coupon = new CouponGenerator();
        coupon.setCouponId(2);
        userCoupons.add(coupon);
        coupons = customerCoupon.filterCouponsByQtyAndDate(coupons, userCoupons);
        Assert.assertEquals(coupons.size(), 0);
    }
}

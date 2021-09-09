package com.eatOut.coupon;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CouponGeneratorTest {
    CouponAbstractFactoryTest couponAbstractFactoryTest = new CouponConcreteFactoryTest();
    ICouponGenerator coupon = couponAbstractFactoryTest.getCouponGenerator();
    ICouponGeneratorDAO couponGeneratorDAO = couponAbstractFactoryTest.getCouponGeneratorDAO();

    @Before()
    public void init() {
        couponAbstractFactoryTest = new CouponConcreteFactoryTest();
        coupon = couponAbstractFactoryTest.getCouponGenerator();
        couponGeneratorDAO = couponAbstractFactoryTest.getCouponGeneratorDAO();
    }

    @Test
    public void generateCouponCode_uniqueCode() {
        String code = coupon.generateCouponCode("$40 Off");
        Assert.assertNotEquals(code, "$40 Off");
    }

    @Test
    public void generateCouponCode_NotEmptyCode() {
        String code = coupon.generateCouponCode("Upto 50%");
        Assert.assertNotEquals(code, " ");
    }

    @Test
    public void generateCouponCode_withEmptyName() {
        String code = coupon.generateCouponCode(" ");
        Assert.assertTrue(code.isEmpty());
    }

    @After
    public void tearDown() {
        coupon = null;
    }
}

package com.eatOut.customercoupon;

import com.eatOut.applicationconfiguration.ApplicationConfiguration;
import com.eatOut.coupon.CouponGenerator;
import com.eatOut.coupon.CouponGeneratorDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController  {
    public static String APPLY_COUPON = "apply-coupon";
    CustomerCouponAbstractFactory customerCouponAbstractFactory = new CustomerCouponConcreteFactory();
    ICustomerCoupon customerCoupon = customerCouponAbstractFactory.getCustomerCoupon();
    ICustomerCouponDAO customerCouponDAO = customerCouponAbstractFactory.getCustomerCouponDAO();

    @RequestMapping(value = "/coupon/apply", method = {RequestMethod.POST})
    public @ResponseBody
    int applyCoupons(String coupon, String customerId) throws Exception {
        String[] couponValues = coupon.split(ApplicationConfiguration.COMMA_SEPARATOR);
        String couponId = couponValues[0];
        String name = couponValues[1];
        String amount = couponValues[2];
        return customerCoupon.applyCoupons(customerCouponDAO, couponId, name, amount, customerId);
    }

    @RequestMapping(value = "/coupon/apply-coupon/{customerId}", method = {RequestMethod.GET})
    public ModelAndView viewCoupons(@PathVariable String customerId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<CouponGenerator> couponList = customerCoupon.loadValidCoupons(customerCouponDAO, new CouponGeneratorDAO(), customerId);
        modelAndView.addObject("couponItems", couponList);
        modelAndView.addObject("customerId", customerId);
        modelAndView.setViewName(APPLY_COUPON);
        return modelAndView;
    }
}

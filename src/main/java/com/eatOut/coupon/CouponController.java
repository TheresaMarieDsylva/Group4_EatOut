package com.eatOut.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CouponController {
    public static String CREATE_COUPON = "create-coupon";

    CouponAbstractFactory couponAbstractFactory = new CouponConcreteFactory();
    ICouponGenerator couponGenerator = couponAbstractFactory.getCouponGenerator();
    ICouponGeneratorDAO couponGeneratorDAO = couponAbstractFactory.getCouponGeneratorDAO();

    @RequestMapping(value = "/coupon", method = {RequestMethod.GET})
    public ModelAndView loadCoupons() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("coupon");
        List<CouponGenerator> couponItems = couponGenerator.loadCoupons(couponGeneratorDAO);
        modelAndView.addObject("couponItems", couponItems);
        return modelAndView;
    }

    @RequestMapping(value = "/coupon/create-coupon", method = {RequestMethod.GET})
    public ModelAndView createCoupons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(CREATE_COUPON);
        return modelAndView;
    }

    @RequestMapping(value = "/coupon/add", method = {RequestMethod.POST})
    public @ResponseBody int addCoupons(String name, String quantity, String amount,
                                        String description, String expiryDate) throws Exception {
        return couponGenerator.addCoupons(couponGeneratorDAO, name, quantity, amount, description, expiryDate);
    }
}

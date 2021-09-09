package com.eatOut.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    ProfileAbstractFactory profileAbstractFactory = new ProfileConcreteFactory();
    IProfile profile = profileAbstractFactory.getProfile();
    IProfileDAO profileDao = profileAbstractFactory.getProfileDao();

    ModelAndView modelAndView = new ModelAndView();
    int tempCusID=1;

    @RequestMapping(value = "/customer_profile", method = {RequestMethod.GET})
    public String customerProfileView() {
        return "customer_profile";
    }

    @RequestMapping(value = "/restaurant_profile", method = {RequestMethod.GET})
    public String restaurantProfileView() {
        return "restaurant_profile";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String logoutView() {
        return "login";
    }

    @RequestMapping(value="/coupon_history", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getCouponsUsed() {
        List<Profile> couponsUsed = profile.showCouponHistoryByCustomer(tempCusID);
        modelAndView.addObject("couponsUsed", couponsUsed);
        return modelAndView;
    }

    @RequestMapping(value="/booking_history", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getBookingHistory() {
        List<Profile> bookingHistory = profile.showBookingHistoryForCustomer(tempCusID);
        modelAndView.addObject("bookingHistory", bookingHistory);
        return modelAndView;
    }

    @RequestMapping(value="/restaurant_booking_history/{restaurantId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getRestaurantBookingHistory(@PathVariable int restaurantId) throws Exception {
        List<Profile> restaurantBookingHistory = null;
        restaurantBookingHistory = profile.showBookingHistoryForRestaurant(profileDao,restaurantId);
        modelAndView.setViewName("restaurant_booking_history");
        modelAndView.addObject("restaurantBookingHistory", restaurantBookingHistory);
        return modelAndView;
    }

    @RequestMapping(value="/order_history", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getOrderHistory() {
        List<Profile> orderHistory = profile.showOrderHistoryForCustomer(tempCusID);
        modelAndView.addObject("orderHistory", orderHistory);
        return modelAndView;
    }

    @RequestMapping(value="/restaurant_order_history/{restaurantId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getRestaurantOrderHistory(@PathVariable int restaurantId) throws Exception {
        List<Profile> restaurantOrderHistory = new ArrayList<>();
        restaurantOrderHistory = profile.showRestaurantOrderHistoryForRestaurant(profileDao,restaurantId);
        modelAndView.setViewName("restaurant_order_history");
        modelAndView.addObject("restaurantOrderHistory", restaurantOrderHistory);
        return modelAndView;
    }

    @RequestMapping(value="/reviews", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getUserReviews() {
        List<Profile> userReviews = profile.showUserReviews(tempCusID);
        modelAndView.addObject("userReviews", userReviews);
        return modelAndView;
    }

    @RequestMapping(value="/restaurant_reviews/{restaurantId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getRestaurantReviews(@PathVariable int restaurantId) throws Exception {
        List<Profile> restaurantReviews = new ArrayList<>();
        restaurantReviews = profile.showReviewsForRestaurant(profileDao,restaurantId);
        modelAndView.setViewName("restaurant_reviews");
        modelAndView.addObject("restaurantReviews", restaurantReviews);
        return modelAndView;
    }
}

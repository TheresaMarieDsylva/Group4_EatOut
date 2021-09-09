package com.eatOut.userhome;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eatOut.deal.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eatOut.restaurant.IRestaurantAddtnlDao;
import com.eatOut.restaurant.IRestaurantAddtnlDtls;
import com.eatOut.restaurant.IRestaurantDao;
import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;
import com.eatOut.restaurant.RestaurantAbstractFactory;
import com.eatOut.restaurant.RestaurantAdditionalDtls;
import com.eatOut.restaurant.RestaurantConcreteFactory;

@Controller
public class CustomerDetailController {

    CustomerAbstractFactory customerAbstractFactory = new CustomerConcreteFactory();
    DealAbstractFactory dealAbstractFactory = new DealConcreteFactory();
    RestaurantAbstractFactory restaurantAbstractFactory = new RestaurantConcreteFactory();

    IRestaurantAddtnlDao restaurantAddtnlDao = restaurantAbstractFactory.getRestuarantAddtnlDAO();
    IRestaurantAddtnlDtls restaurantAddtnlDtls = restaurantAbstractFactory.getRestaurantAddtnlDtls(restaurantAddtnlDao);
    IRestaurantDao restaurantDao = restaurantAbstractFactory.getRestaurantDAO();
    IRestaurantOperation restaurantService = restaurantAbstractFactory.getRestaurantOperation(restaurantAddtnlDtls, restaurantDao);
    ICustomerDao customerDao = customerAbstractFactory.getCustomerDAO();
    ICustomerOperations customerService = customerAbstractFactory.getCustomerOperations(restaurantService, customerDao);
    IDealDAO dealDAO = dealAbstractFactory.getCustomerDealDAO();
    IDealOffer dealOffer = dealAbstractFactory.getCustomerDealOffers();
    
    private static final String RESTAURANT_STATUS="Approved";

    @RequestMapping(value = "/customer-homepage", method = {RequestMethod.GET})
    public String customerHome(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        Map<String, List<String>> locationsMap = restaurantService.getRestaurantLocations();
        model.addAttribute("locationsMap", locationsMap);
        String city = (String) session.getAttribute("city");
        String country = (String) session.getAttribute("country");
        model.addAttribute("customerId", userId);
        List<Restaurant> restList = restaurantService.getRestaurantsByLocationAndStatus(city, country, RESTAURANT_STATUS);
        loadCustomerDetails(session, model, userId, city, country, restList);
        return "customer-homepage";
    }
    
    @RequestMapping(value = "/deal/apply", method = {RequestMethod.POST})
    public @ResponseBody
    int applyDeal(String deal, String customerId) throws Exception {
        String[] deals = deal.split(",");
        String restaurantId = deals[0];
        String dealId = deals[3];
        String price = deals[6];
        return customerService.applyDeals(dealId, price, restaurantId, customerId);
    }

    @RequestMapping(value = "/deal/apply-deal/{customerId}", method = {RequestMethod.GET})
    public ModelAndView viewDealsByCustomer(@PathVariable String customerId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<DealOffer> dealOffers = dealOffer.loadValidDeals(dealDAO);
        modelAndView.addObject("dealOffers", dealOffers);
        modelAndView.addObject("customerId", customerId);
        modelAndView.setViewName("apply-deal");
        return modelAndView;
    }

    @RequestMapping(value = "/restaurantSearchLocation", method = {RequestMethod.POST})
    public String restaurantSearchLocation(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userid");
        String city = request.getParameter("city").trim();
        String country = request.getParameter("country").trim();
        List<Restaurant> restList = restaurantService.getRestaurantsByLocationAndStatus(city, country, RESTAURANT_STATUS);
        loadCustomerDetails(session, model, userId, city, country, restList);
        return "customer-homepage";
    }


    @RequestMapping(value = "/searchRestaurants", method = {RequestMethod.POST})
    public String searchRestaurants(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userid");
        String restaurantName = request.getParameter("searchName");
        String city = request.getParameter("city").trim();
        String country = request.getParameter("country").trim();
        List<Restaurant> restList = restaurantService.searchRestaurant(restaurantName, city, country, RESTAURANT_STATUS);
        loadCustomerDetails(session, model, userId, city, country, restList);
        return "customer-homepage";
    }


    @RequestMapping(value = "/filterRestaurant", method = {RequestMethod.POST})
    public String filterRestaurant(HttpServletRequest request, @ModelAttribute(value = "restaurantAdditionalDtls") RestaurantAdditionalDtls restaurantAdditionalDtls, Model model) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userid");
        String city = (String) session.getAttribute("city");
        String country = (String) session.getAttribute("country");
        List<Restaurant> restList = restaurantAddtnlDtls.getRestaurantDtlsByFilters(restaurantAdditionalDtls, city, country, RESTAURANT_STATUS);
        loadCustomerDetails(session, model, userId, city, country, restList);
        return "customer-homepage";
    }
    

    private void loadCustomerDetails(HttpSession session, Model model, int userId, String city, String country,List<Restaurant> restList) {
		model.addAttribute("restaurantList", restList);
        model.addAttribute("city", city);
        model.addAttribute("country", country);
        List<Restaurant> recommendedLst = customerService.getRecommendedListFrUsr(userId);
        model.addAttribute("recommendedLst", recommendedLst);
        model.addAttribute("restaurantAdditionalDtls", restaurantAddtnlDtls.getRestaurantAddtnlDtlsObj());
        List<String> dealRestaurants = customerService.loadRestaurantByDeals(dealOffer, dealDAO);
        model.addAttribute("dealRestaurants", dealRestaurants);
        int wallet = customerService.calculateCustomerWallet(userId);
        model.addAttribute("wallet", wallet);
        session.setAttribute("customer_wallet", wallet);
	}
    
    
}

package com.eatOut.login;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eatOut.deal.DealAbstractFactory;
import com.eatOut.deal.DealConcreteFactory;
import com.eatOut.deal.IDealDAO;
import com.eatOut.deal.IDealOffer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eatOut.restaurant.IRestaurantAddtnlDao;
import com.eatOut.restaurant.IRestaurantAddtnlDtls;
import com.eatOut.restaurant.IRestaurantDao;
import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;
import com.eatOut.restaurant.RestaurantAbstractFactory;
import com.eatOut.restaurant.RestaurantConcreteFactory;
import com.eatOut.userhome.CustomerAbstractFactory;
import com.eatOut.userhome.CustomerConcreteFactory;
import com.eatOut.userhome.ICustomerDao;
import com.eatOut.userhome.ICustomerOperations;

@Controller
public class UserController {

    CustomerAbstractFactory customerAbstractFactory = new CustomerConcreteFactory();
    RestaurantAbstractFactory resturantAbstractFactory = new RestaurantConcreteFactory();
    DealAbstractFactory dealAbstractFactory = new DealConcreteFactory();
    UserAbstractFactory userAbstractFactory = new UserConcreteFactory();

    IRestaurantAddtnlDao restaurantAddtnlDao = resturantAbstractFactory.getRestuarantAddtnlDAO();
    IRestaurantAddtnlDtls restaurantAddtnlDtls = resturantAbstractFactory.getRestaurantAddtnlDtls(restaurantAddtnlDao);
    IRestaurantDao restaurantDao = resturantAbstractFactory.getRestaurantDAO();
    IRestaurantOperation restaurantService = resturantAbstractFactory.getRestaurantOperation(restaurantAddtnlDtls, restaurantDao);
    ICustomerDao customerDao = customerAbstractFactory.getCustomerDAO();
    ICustomerOperations customerService = customerAbstractFactory.getCustomerOperations(restaurantService, customerDao);
    IDealDAO dealDAO = dealAbstractFactory.getCustomerDealDAO();
    IDealOffer dealOffer = dealAbstractFactory.getCustomerDealOffers();
    IUser userImp = userAbstractFactory.getUser();
    IUserDAO userDAO = userAbstractFactory.getUserDAO();

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String openLogin() {
        return "login";
    }

    @RequestMapping(value = "/customer-login", method = {RequestMethod.GET})
    public String openCustomerLogin() {
        return "customer-login";
    }

    @RequestMapping(value = "/customer-home", method = {RequestMethod.POST})
    public String submitCustomerLogin(HttpServletRequest request, Model model) throws Exception {
        String loginId = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userImp.authenticate(userDAO, loginId, password, UserType.CUSTOMER);
        HttpSession session = request.getSession();
        String city = user.getCity();
        String country = user.getCountry();
        String userId = user.getUserId();
        session.setAttribute("usermail", user.getLoginId());
        session.setAttribute("userid", Integer.parseInt(user.getUserId()));
        session.setAttribute("userId", userId);
        session.setAttribute("username", user.getUserName());
        session.setAttribute("city", city);
        session.setAttribute("country", country);
        model.addAttribute("customerId", userId);
        model.addAttribute("city", city);
        model.addAttribute("country", country);

        List<Restaurant> restList = restaurantService.getRestaurantsByLocationAndStatus(city, country, "Approved");
        model.addAttribute("restaurantList", restList);
        List<Restaurant> recommendedLst = customerService.getRecommendedListFrUsr(Integer.parseInt(user.getUserId()));
        model.addAttribute("recommendedLst", recommendedLst);
        Map<String, List<String>> locationsMap = restaurantService.getRestaurantLocations();
        model.addAttribute("locationsMap", locationsMap);
        model.addAttribute("restaurantAdditionalDtls", restaurantAddtnlDtls.getRestaurantAddtnlDtlsObj());
        List<String> dealRestaurants = customerService.loadRestaurantByDeals(dealOffer, dealDAO);
        model.addAttribute("dealRestaurants", dealRestaurants);
        int wallet = customerService.calculateCustomerWallet(Integer.parseInt(userId));
        model.addAttribute("wallet", wallet);
        session.setAttribute("customer_wallet", wallet);
        return setViewByLoginResponse(user.getLoginResponse(), model, "customer-homepage", "customer-login");
    }

    @RequestMapping(value = "/admin-login", method = {RequestMethod.GET})
    public String openAdminLogin() {
        return "admin-login";
    }

    @RequestMapping(value = "/admin-home", method = {RequestMethod.POST})
    public String submitAdminLogin(HttpServletRequest request, Model model) throws Exception {
        String loginId = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userImp.authenticate(userDAO, loginId, password, UserType.ADMIN);
        return setViewByLoginResponse(user.getLoginResponse(), model, "admin-home", "admin-login");
    }

    @RequestMapping(value = "/restaurant-login", method = {RequestMethod.GET})
    public String openRestaurantLogin() {
        return "restaurant-login";
    }

    @RequestMapping(value = "/restaurant-home", method = {RequestMethod.POST})
    public String submitRestaurantLogin(HttpServletRequest request, Model model) throws Exception {
        String loginId = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userImp.authenticate(userDAO, loginId, password, UserType.RESTAURANT);
        Restaurant restaurant = restaurantService.getRestaurantDtlsByRestId(Integer.parseInt(user.getUserId().trim()));
        model.addAttribute("restaurant", restaurant);
        HttpSession session = request.getSession();
        String userId = user.getUserId();
        session.setAttribute("restaurantId", userId);
        model.addAttribute("restaurantId", userId);
        return setViewByLoginResponse(user.getLoginResponse(), model, "restaurant-home", "restaurant-login");
    }

    private String setViewByLoginResponse(String message, Model model, String homeView, String loginView) {
        String view = null;
        if (message.equals(LoginResponse.SUCCESS.toString())) {
            model.addAttribute("loginAuthMessage", "Successfully logged in!");
            view = homeView;
        } else if (message.equals(LoginResponse.FAILURE.toString())) {
            model.addAttribute("loginAuthMessage", "Login unsuccessful!");
            view = loginView;
        }
        return view;
    }
}
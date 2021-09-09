package com.eatOut.request;

import com.eatOut.restaurant.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RequestController {

    RestaurantAbstractFactory restaurantAbstractFactory = new RestaurantConcreteFactory();
    IRestaurantDao restaurantDao = new RestaurantDaoImpl();
    IRestaurantOperation restaurantOperation = restaurantAbstractFactory.getRestaurantOperation(restaurantDao);

    RequestAbstractFactory requestAbstractFactory = new RequestConcreteFactory();
    IRequest request = requestAbstractFactory.getRequests();
    IRequestDAO requestDAO = requestAbstractFactory.getRequestDAO();

    @RequestMapping(value="/restaurant-req", method = {RequestMethod.GET})
    public ModelAndView loadRequests() {
        ModelAndView modelAndView = new ModelAndView();
        List<Restaurant> requestLst = restaurantOperation.loadRestaurantRequests(restaurantDao);
        modelAndView.setViewName("restaurant-req");
        modelAndView.addObject("requestLst", requestLst);
        return modelAndView;
    }

    @RequestMapping(value = "/restaurant-req/approve", method = {RequestMethod.POST})
    public @ResponseBody int approveRequestByIds(String items) throws Exception {
        return request.approveRequestItemsByIds(requestDAO, items);
    }

    @RequestMapping(value = "/restaurant-req/reject", method = {RequestMethod.POST})
    public @ResponseBody int rejectRequestByIds(String items) throws Exception {
        return request.rejectRequestItemsByIds(requestDAO, items);
    }
}

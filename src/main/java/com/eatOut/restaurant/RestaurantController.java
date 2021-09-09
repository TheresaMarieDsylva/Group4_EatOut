package com.eatOut.restaurant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eatOut.userhome.CustomerAbstractFactory;
import com.eatOut.userhome.CustomerConcreteFactory;
import com.eatOut.userhome.ICustomerDao;
import com.eatOut.userhome.ICustomerOperations;

@Controller
public class RestaurantController {
	
	RestaurantAbstractFactory resturantAbstractFactory=new RestaurantConcreteFactory();
	CustomerAbstractFactory customerAbstractFactory=new CustomerConcreteFactory();
	
	IRestaurantAddtnlDao restaurantAddtnlDao=resturantAbstractFactory.getRestuarantAddtnlDAO();
	IRestaurantAddtnlDtls restaurantAddtnlDtls=resturantAbstractFactory.getRestaurantAddtnlDtls(restaurantAddtnlDao);
	IRestaurantDao restaurantDao=resturantAbstractFactory.getRestaurantDAO();
	IRestaurantOperation restaurantService=resturantAbstractFactory.getRestaurantOperation(restaurantAddtnlDtls, restaurantDao);
	ICustomerDao customerDao=customerAbstractFactory.getCustomerDAO();
	ICustomerOperations customerService=customerAbstractFactory.getCustomerOperations(restaurantService, customerDao);
	

	@RequestMapping(value = "/restaurant-screen/{restaurantId}", method = {RequestMethod.GET})
    public String restaurantScreen(HttpServletRequest request, Model model,@PathVariable String restaurantId) {
		HttpSession session=request.getSession();
		int userId=(int) session.getAttribute("userid");
		Restaurant restaurant=restaurantService.getRestaurantDtlsByRestId(Integer.parseInt(restaurantId.trim()));
		Reviews reviews=new Reviews();
		reviews.setRestaurantId(Integer.parseInt(restaurantId.trim()));
		reviews.setCustomerId(userId);
		model.addAttribute("restaurant",restaurant);
		model.addAttribute("reviews",reviews);
        return "customerhome-restaurants";
    }
	
	@RequestMapping(value = "/restaurant-details/{restaurantId}", method = {RequestMethod.GET})
    public String restaurantDetails(HttpServletRequest request, Model model,@PathVariable String restaurantId) {
		HttpSession session=request.getSession();
		Restaurant restaurant=restaurantService.getRestaurantDtlsByRestId(Integer.parseInt(restaurantId.trim()));
		model.addAttribute("restaurant",restaurant);
		session.setAttribute("restaurantId", restaurant.getRestaurantId());
        return "restaurant-details";
    }
	
	
	@RequestMapping(value = "/updateRestrntAddtnlDtls", method = {RequestMethod.POST})
    public String updateRestrntAddtnlDtls(HttpServletRequest request, @ModelAttribute Restaurant restaurantDtls,Model model) {
		HttpSession session=request.getSession();
		int restuarantId=(int) session.getAttribute("restaurantId");
		RestaurantAdditionalDtls restrntAddtnlDtls=restaurantDtls.getRestaurantAdditionalDtls();
		restrntAddtnlDtls.setRestaurantId(restuarantId);
		restrntAddtnlDtls.addRestaurantAddtnlDtls(restaurantAddtnlDtls);
		Restaurant restaurant=restaurantService.getRestaurantDtlsByRestId(restuarantId);
		model.addAttribute("restaurant",restaurant);
        return "restaurant-details";
    }
	
	@RequestMapping(value = "/addReviews", method = {RequestMethod.POST})
    public String addReviews(HttpServletRequest request, @ModelAttribute(value="restaurantAdditionalDtls") Reviews reviews,Model model) {
		reviews.addRatingsAndReviews(restaurantAddtnlDtls);
		Restaurant restaurant=restaurantService.getRestaurantDtlsByRestId(reviews.getRestaurantId());
		reviews.setRatingsValue(0);
		reviews.setComments("");
		model.addAttribute("restaurant",restaurant);
		model.addAttribute("reviews",reviews);
        return "customerhome-restaurants";
    }
	
}

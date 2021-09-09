package com.eatOut.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CartController {
    CartAbstractFactory cartAbstractFactory = new CartConcreteFactory();
    ICart cart = cartAbstractFactory.getCart();
    ICartDAO cartDao = cartAbstractFactory.getCartDao();
    int customerId;

    @RequestMapping(value = "/item/add", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    String addItem(int restaurantId, String name, String price, int quantity, HttpServletRequest request, Model model) throws Exception {
        HttpSession session=request.getSession();
        customerId= Integer.parseInt(session.getAttribute("userId").toString());
        String response = cart.addItemToCart(cartDao,customerId,restaurantId,name,price,quantity);
        model.addAttribute("cart", response);
        return "menu-customer";
    }

    @RequestMapping(value = "/cart/{restaurantId}", method = RequestMethod.GET)
    public ModelAndView getCartList(@PathVariable int restaurantId, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session=request.getSession();
        customerId= Integer.parseInt(session.getAttribute("userId").toString());
        Map<List<Cart>,Integer> cartItemsListForCustomer = cart.showCartItems(cartDao,customerId,restaurantId);
        Collection<List<Cart>> listOfCartItems = null;
        listOfCartItems = cartItemsListForCustomer.keySet().stream().collect(Collectors.
                toCollection(ArrayList::new));
        modelAndView.setViewName("cart");
        session=request.getSession();
        List<Cart> cartItemsList= ((ArrayList<List<Cart>>) listOfCartItems).get(0);
        Collection<Integer> amount=cartItemsListForCustomer.values();
        modelAndView.addObject("cartItemsList",cartItemsList);
        modelAndView.addObject("totalAmount",amount);
        return modelAndView;
    }

    @RequestMapping(value = "/item/remove", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    String removeItem(int restaurantId, String name, HttpServletRequest request,Model model) throws Exception {
        HttpSession session=request.getSession();
        customerId= Integer.parseInt(session.getAttribute("userId").toString());
        String response = cart.removeItemFromCart(cartDao,customerId,restaurantId,name);
        model.addAttribute("cartResponse", response);
        return "menu-customer";
    }

    @RequestMapping(value = "/order-create/{restaurantId}", method = {RequestMethod.GET,RequestMethod.POST})
    public String submitOrder(@PathVariable int restaurantId, HttpServletRequest request, Model model) throws Exception {
        String displayPage = "";
        HttpSession session=request.getSession();
        customerId= Integer.parseInt(session.getAttribute("userId").toString());
        String value = "";
            value = cart.submitOrder(cartDao,customerId,restaurantId);
        if (value.equals(Cart.OrderResponse.ORDER_CREATION_SUCCESS.name())) {
            model.addAttribute("cartResponse", "Order is created");
            displayPage = "order";
        } else {
            if (value.equals(Cart.OrderResponse.ORDER_CREATION_FAILED.name())) {
                model.addAttribute("cartResponse", "Order Creation failed. Try again!");
                displayPage = "cart";
            }
        }
        return displayPage;
    }
}

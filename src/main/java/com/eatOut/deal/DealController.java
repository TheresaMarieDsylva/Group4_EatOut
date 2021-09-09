package com.eatOut.deal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.eatOut.applicationconfiguration.ApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.eatOut.restaurant.*;

@Controller
public class DealController {

    DealAbstractFactory dealAbstractFactory = new DealConcreteFactory();
    RestaurantAbstractFactory restaurantAbstractFactory = new RestaurantConcreteFactory();

    IDealOffer dealOffer = dealAbstractFactory.getCustomerDealOffers();
    IDealDAO dealDao = dealAbstractFactory.getCustomerDealDAO();

    IRestaurantDao restaurantDao = restaurantAbstractFactory.getRestaurantDAO();
    IRestaurantOperation restaurantOperation = restaurantAbstractFactory.getRestaurantOperation(restaurantDao);

    @RequestMapping(value = "/deals", method = RequestMethod.GET)
    public ModelAndView viewDeals() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deals");
        List<DealOffer> dealOffers = dealOffer.loadValidDeals(dealDao);
        modelAndView.addObject("dealOffers", dealOffers);
        return modelAndView;
    }

    @RequestMapping(value = "/deals/{restaurantId}", method = RequestMethod.GET)
    public ModelAndView viewDealsByRestaurant(@PathVariable String restaurantId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deals");
        List<DealOffer> dealOffers = dealOffer.loadValidDeals(dealDao);
        dealOffers = dealOffers.stream().filter(dOffer -> restaurantId.equals(dOffer.getRestaurantId())).collect(Collectors.toList());
        modelAndView.addObject("dealOffers", dealOffers);
        return modelAndView;
    }

    @RequestMapping(value = "/deals/createdeal/{restaurantId}", method = RequestMethod.GET)
    public String createDealByRestaurant(Model model, @PathVariable String restaurantId) throws Exception {
        List<String> dealItems = new ArrayList<>();
        dealItems.add("Select");
        model.addAttribute("items", dealItems);
        List<String> restaurantItems = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        countries.add("Select");
        countries.addAll(dealOffer.getRestaurantCountries(restaurantOperation));
        model.addAttribute("countries", countries);
        restaurantItems.add("Select");
        List<Restaurant> restaurants = dealOffer.loadRestaurantsByLocation(restaurantOperation, countries.get(1));
        restaurants = restaurants.stream().filter(rest -> restaurantId.equals(String.valueOf(rest.getRestaurantId()))).collect(Collectors.toList());
        restaurants.forEach(rest -> {
            restaurantItems.add(rest.getRestaurantId() + ApplicationConfiguration.HYPHEN_SEPARATOR + rest.getRestaurantName());
        });
        model.addAttribute("restaurants", restaurantItems);
        return "create_deal";
    }

    @RequestMapping(value = "/deals/createdeal", method = RequestMethod.GET)
    public String createDeal(Model model) throws Exception {
        List<String> dealItems = new ArrayList<>();
        dealItems.add("Select");
        model.addAttribute("items", dealItems);
        List<String> restaurantItems = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        countries.add("Select");
        countries.addAll(dealOffer.getRestaurantCountries(restaurantOperation));
        model.addAttribute("countries", countries);
        restaurantItems.add("Select");
        List<Restaurant> restaurants = dealOffer.loadRestaurantsByLocation(restaurantOperation, countries.get(1));
        restaurants.forEach(rest -> {
            restaurantItems.add(rest.getRestaurantId() + ApplicationConfiguration.HYPHEN_SEPARATOR + rest.getRestaurantName());
        });
        model.addAttribute("restaurants", restaurantItems);
        return "create_deal";
    }

    @RequestMapping(value = "/deals/load-items", method = RequestMethod.POST)
    public @ResponseBody
    String loadRestaurants(String selectedRestaurant, String selectedDealType, String selectedCountry, Model model) throws Exception {
        StringBuilder restaurantBuilder = new StringBuilder();
        List<Restaurant> restaurants = dealOffer.loadRestaurantsByLocation(restaurantOperation, selectedCountry);
        if(selectedRestaurant != null) {
            restaurants = restaurants.stream().filter(rest -> selectedRestaurant.equals(String.valueOf(rest.getRestaurantId()))).collect(Collectors.toList());
        }
        restaurants.forEach(rest -> {
            restaurantBuilder.append(rest.getRestaurantId()).append(ApplicationConfiguration.HYPHEN_SEPARATOR)
                    .append(rest.getRestaurantName()).append(ApplicationConfiguration.LINE_SEPARATOR);
        });
        model.addAttribute("restaurants", restaurantBuilder.append(ApplicationConfiguration.TAB_SEPARATOR));
        Map<String, Double> dealItem = dealOffer.loadRestaurantItemsByDealType(getDeal(selectedDealType));
        for (Map.Entry<String, Double> item : dealItem.entrySet()) {
            model.addAttribute("items", restaurantBuilder.append(item.getKey())
                    .append(ApplicationConfiguration.SYMBOL_SEPARATOR).append(item.getValue()).append(ApplicationConfiguration.TAB_SEPARATOR));
        }
        return restaurantBuilder.toString();
    }

    private Deal getDeal(String selectedDealType) {
        if (selectedDealType.equals("dining")) {
            return new Dining();
        } else if (selectedDealType.equals("takeaway")) {
            return new Takeaway();
        }
        return null;
    }

    @RequestMapping(value = "/deals/add", method = RequestMethod.POST)
    public @ResponseBody int submitDeal(String selectedRestaurant, String selectedDealType, String newPrice, String dealItem, String endDate) throws Exception {
        String[] restaurant = selectedRestaurant.split(ApplicationConfiguration.HYPHEN_SEPARATOR);
        int restaurantId = Integer.parseInt(restaurant[0]);
        String[] dealItems = dealItem.split(ApplicationConfiguration.SEPARATOR);
        return dealOffer.createDeal(dealDao, selectedDealType, restaurantId, newPrice, dealItems[0], dealItems[1], endDate);
    }
}

package com.eatOut.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.eatOut.menu.Menu.MenuItemResponse;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MenuController {

    MenuAbstractFactory menuAbstractFactory = new MenuConcreteFactory();
    IMenu menu = menuAbstractFactory.getMenu();
    IMenuDAO menuDAO = menuAbstractFactory.getMenuDao();

   @RequestMapping(value = "/menuDisplay/{restaurantId}", method = RequestMethod.GET)
        public ModelAndView getMenuListForRestaurant(@PathVariable String restaurantId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<Menu> menuItemsList = new ArrayList<>();
        menuItemsList = menu.showMenu(menuDAO,restaurantId);
        modelAndView.setViewName("menuDisplay");
        modelAndView.addObject("menuItemsList", menuItemsList);
        return modelAndView;
    }

    @RequestMapping(value = "/menu-customer/{restaurantId}", method = RequestMethod.GET)
    public ModelAndView getMenuListForCustomer(@PathVariable String restaurantId) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        List<Menu> menuItemsListForCustomer = new ArrayList<>();
        menuItemsListForCustomer = menu.showMenu(menuDAO,restaurantId);
        modelAndView.setViewName("menu-customer");
        modelAndView.addObject("menuItemsListForCustomer", menuItemsListForCustomer);
        return modelAndView;
    }

    @RequestMapping(value = "/menuItemAdd/{restaurantId}", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewAddMenuItemPage(HttpServletRequest request, @PathVariable String restaurantId, Model model) throws Exception{

        String displayPage="";
        String menuItemName = request.getParameter("menuItemName");
        String menuItemDescription = request.getParameter("menuItemDescription");
        String menuItemPrice = request.getParameter("itemPrice");
        String menuItemQuantity = request.getParameter("itemQuantity");

        if (request.getParameter("menu_item_addition_form") == null) {
            displayPage = "menuItemAdd";
        } else {
            String value = menu.addMenuItem(menuDAO,restaurantId, menuItemName, menuItemDescription, menuItemPrice, menuItemQuantity);
            if (value.equals(MenuItemResponse.MENU_ITEM_ALREADY_EXISTS.name())) {
                model.addAttribute("menuItemAdditionMessage", "Item already present. Try adding another item!");
                displayPage = "menuItemAdd";
            } else if (value.equals(MenuItemResponse.MENU_ITEM_CREATED.name())) {
                model.addAttribute("menuItemAdditionMessage", "Item is added in menu..");
                displayPage = "menuItemAdd";
            } else {
                displayPage = "menuItemAdd";
            }
        }
        return displayPage;
    }

    @RequestMapping(value = "/menuItemDelete/{restaurantId}", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewDeleteMenuItemPage(HttpServletRequest request,@PathVariable String restaurantId, Model model) throws Exception{
        String displayPage="";
        String menuItemName = request.getParameter("menuItemName");

        if (request.getParameter("menu_item_deletion_form") == null) {
            displayPage = "menuItemDelete";
        } else {
            String value = menu.deleteMenuItem(menuDAO,restaurantId,menuItemName);
            if (value.equals(MenuItemResponse.MENU_ITEM_DOESNT_EXISTS.name())) {
                model.addAttribute("menuItemDeletionMessage", "Item is not present. Try deleting another item!");
                displayPage = "menuItemDelete";
            } else if (value.equals(MenuItemResponse.MENU_ITEM_DELETED.name())) {
                model.addAttribute("menuItemDeletionMessage", "Item is deleted from menu..");
                displayPage = "menuItemDelete";
            } else {
                displayPage = "menuItemDelete";
            }
        }
        return displayPage;
    }
}


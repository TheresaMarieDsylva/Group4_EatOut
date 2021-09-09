package com.eatOut.menu;

import java.util.List;

public interface IMenu {

     List<Menu> showMenu(IMenuDAO menuDAO, String restaurantId) throws Exception;
     String addMenuItem(IMenuDAO menuDAO, String restaurantId,String menuItemName, String menuItemDescription, String menuItemPrice, String menuItemQuantity) throws Exception;
     String deleteMenuItem(IMenuDAO menuDAO,String restaurantId,String menuItemName) throws Exception;
}

package com.eatOut.menu;

import java.util.List;

public interface IMenuDAO {

    List<Menu> showItemsFromDB(String restaurantId) throws Exception;
    Boolean checkIfItemPresentInDB(String restaurantId, String menuItemName) throws Exception;
    Boolean addItemInDB(String restaurantId, String menuItemName, String menuItemDescription, String menuItemPrice, String menuItemQuantity) throws Exception;
    Boolean removeItemInDB(String restaurantId, String menuItemName) throws Exception;
}

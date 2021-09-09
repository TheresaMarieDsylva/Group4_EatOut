package com.eatOut.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu implements IMenu{

    String restaurantId;
    String itemName;
    String itemDescription;
    String itemPrice;
    String itemQuantity;
    String response=null;

    public enum MenuItemResponse {
        MENU_ITEM_ALREADY_EXISTS,
        MENU_ITEM_DOESNT_EXISTS,
        MENU_ITEM_CREATED,
        MENU_ITEM_DELETED
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
    
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    public String getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public List<Menu> showMenu(IMenuDAO menuDAO, String restaurantId) throws Exception {
        List<Menu> menuItemsfromDB = new ArrayList<>();
        try {
            menuItemsfromDB = menuDAO.showItemsFromDB(restaurantId);
        } catch (Exception e) {
            throw new Exception("Exception occurred while fetching items from Menu");
        }
        return menuItemsfromDB;
    }

    @Override
    public String addMenuItem(IMenuDAO menuDAO, String restaurantId, String menuItemName, String menuItemDescription, String menuItemPrice, String menuItemQuantity) throws Exception {
        Boolean addItem=false;
        try{
            addItem= menuDAO.checkIfItemPresentInDB(restaurantId, menuItemName);
            if(addItem){
                response = MenuItemResponse.MENU_ITEM_ALREADY_EXISTS.name();
                } else {
                addItem = menuDAO.addItemInDB(restaurantId, menuItemName, menuItemDescription, menuItemPrice, menuItemQuantity);
                response = MenuItemResponse.MENU_ITEM_CREATED.name();
                }
            } catch (Exception e) {
                throw new Exception("Exception occurred while adding item from Menu");
            }
        return response;
    }

    @Override
    public String deleteMenuItem(IMenuDAO menuDAO,String restaurantId, String menuItemName) throws Exception{
        Boolean deleteItem=false;
        try{
            deleteItem= menuDAO.checkIfItemPresentInDB(restaurantId, menuItemName);
            if(deleteItem){
                deleteItem=menuDAO.removeItemInDB(restaurantId,menuItemName);
                response = MenuItemResponse.MENU_ITEM_DELETED.name();
            } else {
                response = MenuItemResponse.MENU_ITEM_DOESNT_EXISTS.name();
            }
        }catch (Exception e) {
            throw new Exception("Exception occurred while removing item from Menu");
        }
        return response;
    }
}

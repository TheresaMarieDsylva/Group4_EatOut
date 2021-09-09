package com.eatOut.menu;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuDAO implements IMenuDAO{

    @Override
    public List<Menu> showItemsFromDB(String restaurantId) throws Exception {
        List<Menu> menuList= new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getMenuDetailsForRestaurant(?)");
            storedProcedure.setParameter(1, restaurantId);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> row : tableList) {
                Menu menuItem = new Menu();
                menuItem.setItemName(row.get("menu_item_name").toString());
                menuItem.setItemDescription(row.get("menu_item_description").toString());
                menuItem.setItemPrice(row.get("menu_item_price").toString());
                menuItem.setItemQuantity(row.get("menu_item_quantity").toString());
                menuList.add(menuItem);
            }
        } catch (Exception ex) {
            throw new Exception("Not able to fetch items from Menu");
        }
        return menuList;
    }

    @Override
    public Boolean checkIfItemPresentInDB(String restaurantId, String menuItemName) throws Exception {
        Boolean value=false;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("menuItemIfExists(?,?)");
            storedProcedure.setParameter(1, restaurantId);
            storedProcedure.setParameter(2, menuItemName);
            Map<String, Object> record = storedProcedure.getTableRecord();
            if (record.isEmpty()) {
                value=false;
            }
            else{
                value = true;
            }
        } catch (Exception ex) {
            throw new Exception("Not able to find if item is available in Menu");
        }
        return value;
    }

    @Override
    public Boolean addItemInDB(String restaurantId, String menuItemName, String menuItemDescription, String menuItemPrice, String menuItemQuantity) throws Exception {
        int row = 0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertItemIntoMenu(?,?,?,?,?)");
            storedProcedure.setParameter(1, restaurantId);
            storedProcedure.setParameter(2, menuItemName);
            storedProcedure.setParameter(3, menuItemDescription);
            storedProcedure.setParameter(4, menuItemPrice);
            storedProcedure.setParameter(5, menuItemQuantity);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to add item in Menu");
        }
        return fetchValueByRowCount(row);
    }

    @Override
    public Boolean removeItemInDB(String restaurantId, String menuItemName) throws Exception {
    int row=0;
    try {
            IStoredProcedure storedProcedure = new StoredProcedure("removeItemFromMenu(?,?)");
            storedProcedure.setParameter(1, restaurantId);
            storedProcedure.setParameter(2, menuItemName);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to remove item in Menu");
        }
        return fetchValueByRowCount(row);
    }

    Boolean fetchValueByRowCount(int row){
        Boolean value;
        if (row > 0) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }
}

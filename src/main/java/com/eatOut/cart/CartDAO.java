package com.eatOut.cart;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDAO implements ICartDAO{

    @Override
    public Boolean addToCartInDB(int customerId, int restaurantId, String itemName, String itemPrice, int itemQuantity) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertItemIntoCart(?,?,?,?,?)");
            storedProcedure.setParameter(1, customerId);
            storedProcedure.setParameter(2, restaurantId);
            storedProcedure.setParameter(3, itemName);
            storedProcedure.setParameter(4, itemPrice);
            storedProcedure.setParameter(5, itemQuantity);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to add item in Cart");
        }
        return fetchValueByRowCount(row);
    }

    @Override
    public Boolean removeFromCartInDB(int customerId, int restaurantId, String itemName) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("removeItemFromCart(?,?)");
            storedProcedure.setParameter(1, itemName);
            storedProcedure.setParameter(2, restaurantId);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to remove item in Cart");
        }
        return fetchValueByRowCount(row);
    }

    @Override
    public List<Cart> showCartItemsFromDB(int customerId, int restaurantId) throws Exception {
        List<Cart> list=new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getCartItems(?,?)");
            storedProcedure.setParameter(1, customerId);
            storedProcedure.setParameter(2, restaurantId);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            for (Map<String, Object> rw : tableList) {
                Cart cartItems = new Cart();
                cartItems.setItemName(rw.get("menu_item_name").toString());
                cartItems.setItemPrice(rw.get("item_price").toString());
                cartItems.setItemQuantity(Integer.parseInt(rw.get("menu_item_quantity").toString()));
                list.add(cartItems);
            }
        } catch (Exception ex) {
            throw new Exception("Not able to fetch items from Cart");
        }
        return list;
    }

    @Override
    public Boolean insertOrderDetailInDB(int orderBillAmount, int customerId, int restaurantId, String itemName, int itemQuantity) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("insertOrderForCustomer(?,?,?,?,?)");
            storedProcedure.setParameter(1, orderBillAmount);
            storedProcedure.setParameter(2, customerId);
            storedProcedure.setParameter(3, restaurantId);
            storedProcedure.setParameter(4, itemName);
            storedProcedure.setParameter(5, itemQuantity);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to insert order");
        }
        return fetchValueByRowCount(row);
    }

    @Override
    public Boolean removeItemsFromCartAfterOrderDB(int customerId, int restaurantId) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("deleteCartItemsAfterOrder(?,?)");
            storedProcedure.setParameter(1, customerId);
            storedProcedure.setParameter(2, restaurantId);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to clear cart after order");
        }
        return fetchValueByRowCount(row);
    }

    Boolean fetchValueByRowCount(int row){
        Boolean value= false;
        if (row > 0) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

}

package com.eatOut.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart implements ICart{

    String itemName;
    String itemPrice;
    int itemQuantity;
    int totalAmount;
    String response=null;
    ICalculate calculate=new Calculate();

    public enum CartItemResponse {
        ITEM_ADDED_TO_CART,
        ITEM_NOT_ADDED_TO_CART,
        ITEM_REMOVED_FROM_CART,
        ITEM_NOT_REMOVED_FROM_CART
    }

    public enum OrderResponse {
        ORDER_CREATION_SUCCESS,
        ORDER_CREATION_FAILED
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) { this.itemPrice = itemPrice; }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public Map<List<Cart>,Integer> showCartItems(ICartDAO cartDAO,int customerId, int restaurantId) throws Exception{
        int amount=0;
        Map<List<Cart>,Integer> list= new HashMap<>();
        List<Cart> cartItems = new ArrayList<>();
        try{
            cartItems = cartDAO.showCartItemsFromDB(customerId,restaurantId);
            List<Object> obj = new ArrayList<>();
            obj=calculate.calculateParameters(cartItems);
            amount = (Integer) obj.get(2);
            list.put(cartItems,amount);
        } catch (Exception e) {
            throw new Exception("Exception occurred while fetching cart items");
        }
        return list;
    }

    @Override
    public String addItemToCart(ICartDAO cartDAO,int customerId, int restaurantId, String itemName, String itemPrice, int itemQuantity) throws Exception{
        Boolean value=false;
        try{
            value=cartDAO.addToCartInDB(customerId,restaurantId,itemName,itemPrice,itemQuantity);
            if(value){
                response = Cart.CartItemResponse.ITEM_ADDED_TO_CART.name();
            }
            else{
                response = Cart.CartItemResponse.ITEM_NOT_ADDED_TO_CART.name();
            }
        } catch (Exception e) {
            throw new Exception("Exception occurred while adding items to cart");
        }
        return response;
    }

    @Override
    public String removeItemFromCart(ICartDAO cartDAO,int customerId, int restaurantId, String itemName) throws Exception{
        Boolean value=false;
        try{
            value=cartDAO.removeFromCartInDB(customerId,restaurantId,itemName);
            if(value){
                response = Cart.CartItemResponse.ITEM_REMOVED_FROM_CART.name();
            } else{
            response = Cart.CartItemResponse.ITEM_NOT_REMOVED_FROM_CART.name();
            }
        } catch (Exception e) {
            throw new Exception("Exception occurred while removing items from cart");
        }
        return response;
    }

    @Override
    public String submitOrder(ICartDAO cartDAO,int customerId, int restaurantId) throws Exception {
        int amount=0;
        int totalQuantity = 0;
        String itemNames="";
        Boolean value = false;
        List<Cart> cartItems = new ArrayList<>();
        try{
            cartItems = cartDAO.showCartItemsFromDB(customerId, restaurantId);
            if (cartItems.size() > 0) {
            List<Object> obj = calculate.calculateParameters(cartItems);
            for (int i = 0; i < obj.size(); i++) {
                itemNames = String.valueOf(obj.get(0));
                totalQuantity = (Integer) obj.get(1);
                amount = (Integer) obj.get(2);
            }
                value = cartDAO.insertOrderDetailInDB(amount, customerId, restaurantId, itemNames, totalQuantity);
            if (value) {
                response = OrderResponse.ORDER_CREATION_SUCCESS.name();
                try {
                    cartDAO.removeItemsFromCartAfterOrderDB(customerId,restaurantId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                response = OrderResponse.ORDER_CREATION_FAILED.name();
            }
        } else{
            response = OrderResponse.ORDER_CREATION_FAILED.name();
        }
        } catch (Exception e) {
            throw new Exception("Exception occurred while submitting order");
        }
        return response;
    }

}

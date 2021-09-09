package com.eatOut.cart;

import java.util.List;
import java.util.Map;

public interface ICart {
     Map<List<Cart>, Integer> showCartItems(ICartDAO cartDAO,int customerId, int restaurantId) throws Exception;
     String addItemToCart(ICartDAO cartDAO, int customerId, int restaurantId, String itemName, String price, int itemQuantity) throws Exception;
     String removeItemFromCart(ICartDAO cartDAO, int customerId, int restaurantId, String itemName) throws Exception;
     String submitOrder(ICartDAO cartDAO,int customerId, int restaurantId) throws Exception;
}

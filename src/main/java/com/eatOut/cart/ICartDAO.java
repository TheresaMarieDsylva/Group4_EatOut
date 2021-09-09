package com.eatOut.cart;

import java.util.List;

public interface ICartDAO {
    public Boolean addToCartInDB(int customer_id, int restaurant_id, String itemName, String price, int item_quantity) throws Exception;
    public Boolean removeFromCartInDB(int customer_id, int restaurant_id, String itemName) throws Exception;
    public List<Cart> showCartItemsFromDB(int customerID, int restaurantID) throws Exception;
    public Boolean insertOrderDetailInDB(int order_bill_amount, int customer_id, int restaurant_id, String itemName, int quantity) throws Exception;
    public Boolean removeItemsFromCartAfterOrderDB(int customer_id, int restaurant_id) throws Exception;
}

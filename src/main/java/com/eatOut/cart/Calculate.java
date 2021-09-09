package com.eatOut.cart;

import java.util.Arrays;
import java.util.List;

public class Calculate implements ICalculate{

    String name="";
    int quantity;
    int totalPrice;

    @Override
    public List<Object> calculateParameters(List<Cart> cartItems) {

        for (int i = 0; i < cartItems.size(); i++) {
            name = name + cartItems.get(i).getItemName() + ",";
            quantity += cartItems.get(i).getItemQuantity();
            totalPrice += Integer.valueOf(cartItems.get(i).getItemPrice()) * cartItems.get(i).getItemQuantity();
            }
       return Arrays.asList(name, quantity, totalPrice);
    }
}

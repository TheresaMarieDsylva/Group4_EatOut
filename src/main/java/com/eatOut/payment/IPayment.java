package com.eatOut.payment;

public interface  IPayment {
    public abstract Boolean checkIfHasEnoughMoneyAndPay(String customerID, String billAmount, String orderType, String restaurantID);
}

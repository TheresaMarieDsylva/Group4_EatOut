package com.eatOut.payment;

import java.util.ArrayList;
import java.util.List;

public class Payment implements IPayment {
    IPaymentDAO paymentDAO = new PaymentDAO();
    String couponAmount;

    @Override
    public Boolean checkIfHasEnoughMoneyAndPay(String customerID, String billAmount, String orderType, String restaurantID) {
        Boolean response = false;
        int cAmount, memDiscount;
        List<String> memValues = new ArrayList<>();
        List<Integer> memValuesTypeInt = new ArrayList<>();
        int billAmt = Integer.parseInt(billAmount);

        memValues  = paymentDAO.getMembershipPercent(customerID);

        if(memValues.size()>0){
            for(String s : memValues) {
                memValuesTypeInt.add(Integer.valueOf(s));
            }
        }

            if(memValues.size()==0){
                billAmt=billAmt;

            }
            else if(orderType.equals("booking") && memValues.size()>0){
                memDiscount = (billAmt*memValuesTypeInt.get(0))/100;
                billAmt=billAmt-memDiscount;
            }
            else if(orderType.equals("takeout") && memValues.size()>0) {
                memDiscount = (billAmt*memValuesTypeInt.get(1))/100;
                billAmt=billAmt-memDiscount;
            }
           paymentDAO.enterTransaction(customerID,billAmt);

            response=true;


        System.out.println(billAmt);
        return response;
    }


}

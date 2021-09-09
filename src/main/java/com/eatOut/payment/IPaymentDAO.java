package com.eatOut.payment;

import java.util.List;

public interface IPaymentDAO {

    List<String> getMembershipPercent(String customerID);

    Boolean enterTransaction(String customerID, int billAmount);
}

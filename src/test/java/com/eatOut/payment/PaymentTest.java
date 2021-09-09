package com.eatOut.payment;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentTest {
    static IPayment iPayment =null;
    static IPaymentDAO iPaymentDAO = null;


    @BeforeClass
    public static void setUp() {
        iPayment = new Payment();
        iPaymentDAO = mock(IPaymentDAO.class);
    }

    @Test
    public static void enterTransactionTest(){
        when(iPaymentDAO.enterTransaction("1",200)).thenReturn(true);
        Assert.assertEquals(true, iPaymentDAO.enterTransaction("1",100));
        when(iPaymentDAO.enterTransaction("2",20000)).thenReturn(false);
        Assert.assertEquals(false, iPaymentDAO.enterTransaction("2",20000));
    }

    @Test
    public static void checkIfHasEnoughMoneyAndPayTest(){
        when(iPayment.checkIfHasEnoughMoneyAndPay("1","200","booking","2")).thenReturn(true);
        Assert.assertEquals(true, iPayment.checkIfHasEnoughMoneyAndPay("1","200","booking","2"));
        when(iPayment.checkIfHasEnoughMoneyAndPay("1","200","orderbooking","2")).thenReturn(false);
        Assert.assertEquals(false, iPayment.checkIfHasEnoughMoneyAndPay("1","200","orderbooking","2"));
    }


    @AfterClass
    public static void tearDown() {
        iPayment = null;
        iPaymentDAO = null;
    }
}

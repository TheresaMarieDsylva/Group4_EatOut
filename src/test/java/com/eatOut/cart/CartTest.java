package com.eatOut.cart;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartTest {
    CartAbstractFactory cartAbstractFactory;
    ICart cart;
    ICartDAO cartDAO;

    @Before()
    public void init() {
        cartAbstractFactory = new CartConcreteFactoryTest();
        cart= cartAbstractFactory.getCart();
        cartDAO=cartAbstractFactory.getCartDao();
    }

    @Test
    public void showCartItemsTest() throws Exception {
        when(cartDAO.showCartItemsFromDB(1, 1)).thenReturn(new ArrayList<Cart>());
        assertEquals(new ArrayList<Cart>(), cartDAO.showCartItemsFromDB(1,1));
    }

    @Test
    public void addToCartTest() throws Exception{
        when(cartDAO.addToCartInDB(1,2,"pasta","3", 2)).thenReturn(true);
        assertEquals(true,cartDAO.addToCartInDB(1,2,"pasta","3", 2));
    }

    @Test
    public void addToCartFailTest() throws Exception{
        when(cartDAO.addToCartInDB(1,3,"pizza","5", 1)).thenReturn(false);
        assertNotEquals(true,cartDAO.addToCartInDB(1,3,"pizza","5", 1));
    }

    @Test
    public void removeFromCartTest() throws Exception{
        when(cartDAO.removeFromCartInDB(1,3,"pizza")).thenReturn(true);
        assertEquals(true,cartDAO.removeFromCartInDB(1,3,"pizza"));
    }

    @Test
    public void removeFromCartFailTest() throws Exception{
        when(cartDAO.removeFromCartInDB(1,3,"juice")).thenReturn(false);
        assertNotEquals(true,cartDAO.removeFromCartInDB(1,3,"juice"));
    }

    @Test
    public void submitOrderTest() throws Exception{
        when(cartDAO.insertOrderDetailInDB(10,1,2,"juice", 2)).thenReturn(true);
        assertEquals(true,cartDAO.insertOrderDetailInDB(10,1,2,"juice", 2));
    }

    @Test
    public void removeItemsFromCartAfterOrderDBTest() throws Exception{
        when(cartDAO.removeItemsFromCartAfterOrderDB(1,2)).thenReturn(true);
        assertEquals(true,cartDAO.removeItemsFromCartAfterOrderDB(1,2));
    }

    @After
    public void tearDown() {
        cart = null;
        cartDAO=null;
    }
}

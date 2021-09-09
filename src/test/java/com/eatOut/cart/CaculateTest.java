package com.eatOut.cart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class CaculateTest {
    private static ICalculate calculate;
    List<Object> obj;

    @Before
    public void setUp() {
        calculate = mock(Calculate.class);
    }

    @Test
    public void calculateParametersTest() {
    List<Cart> cartItems= new ArrayList<>();
    Cart cart= new Cart();
    cart.setItemName("PIZZA");
    cart.setItemPrice("2");
    cart.setItemQuantity(2);
    cartItems.add(cart);
    cart= new Cart();
    cart.setItemName("Pasta");
    cart.setItemPrice("4");
    cart.setItemQuantity(1);
    cartItems.add(cart);
    when(calculate.calculateParameters(cartItems)).thenReturn(new ArrayList<>());
    assertEquals(new ArrayList<>(),calculate.calculateParameters(cartItems));
    }

    @After
    public void tearDown() {
        calculate = null;
    }
}

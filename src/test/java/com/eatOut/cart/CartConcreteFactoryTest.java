package com.eatOut.cart;

import static org.mockito.Mockito.mock;

public class CartConcreteFactoryTest extends CartAbstractFactory {

    @Override
    public ICart getCart() { return new Cart(); }

    @Override
    public ICartDAO getCartDao() { return mock(CartDAO.class); }
}

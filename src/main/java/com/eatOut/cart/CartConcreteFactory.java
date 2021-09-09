package com.eatOut.cart;

public class CartConcreteFactory extends CartAbstractFactory{

    @Override
    public ICart getCart() { return new Cart(); }

    @Override
    public ICartDAO getCartDao() { return new CartDAO(); }
}

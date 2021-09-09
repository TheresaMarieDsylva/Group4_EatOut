package com.eatOut.cart;

public abstract class CartAbstractFactory {

        public abstract ICart getCart();

        public abstract ICartDAO getCartDao();
    }


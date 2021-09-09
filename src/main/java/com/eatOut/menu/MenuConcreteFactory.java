package com.eatOut.menu;

public class MenuConcreteFactory extends MenuAbstractFactory{

    @Override
    public IMenu getMenu() { return new Menu(); }

    @Override
    public IMenuDAO getMenuDao() { return new MenuDAO(); }
}

package com.eatOut.menu;

import static org.mockito.Mockito.mock;

public class MenuConcreteFactoryTest extends MenuAbstractFactory{
    @Override
    public IMenu getMenu() { return new Menu(); }

    @Override
    public IMenuDAO getMenuDao() { return mock(MenuDAO.class); }
}

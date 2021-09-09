package com.eatOut.menu;

import org.junit.*;
import java.util.ArrayList;
import static org.mockito.Mockito.when;

public class MenuTest {

    MenuAbstractFactory menuAbstractFactory;
    IMenu menu;
    IMenuDAO menuDAO;

    @Before()
    public void init() {
        menuAbstractFactory=new MenuConcreteFactoryTest();
        menu=menuAbstractFactory.getMenu();
        menuDAO=menuAbstractFactory.getMenuDao();
    }

    @Test
    public void checkDBIItemExistsTest() throws Exception{
        when(menuDAO.checkIfItemPresentInDB("3","pineapple")).thenReturn(true);
        Assert.assertEquals(true, menuDAO.checkIfItemPresentInDB("3","pineapple"));
        when(menuDAO.checkIfItemPresentInDB("3","cake")).thenReturn(false);
        Assert.assertNotEquals(true,menuDAO.checkIfItemPresentInDB("3","cake"));

    }

    @Test
    public void showMenuForCustomerTest() throws Exception {
        when(menuDAO.showItemsFromDB("1")).thenReturn(new ArrayList<Menu>());
        Assert.assertEquals(new ArrayList<Menu>(), menuDAO.showItemsFromDB("1"));
    }

    @Test
    public void addItemInDBTest() throws Exception {
            when(menuDAO.addItemInDB("3","Cashewnut","Dry fruit","30 CAD","12")).thenReturn(true);
            Assert.assertNotEquals(false, menuDAO.addItemInDB("3","Cashewnut", "Dry fruit", "30 CAD", "12"));
    }

    @Test
    public void removeItemInDBTest() throws Exception {
            when(menuDAO.removeItemInDB("3","Cashewnut")).thenReturn(true);
            Assert.assertNotEquals(false, menuDAO.removeItemInDB("3","Cashewnut"));
    }

}

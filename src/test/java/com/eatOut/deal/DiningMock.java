package com.eatOut.deal;

import java.util.HashMap;
import java.util.Map;

public class DiningMock extends Deal {
    Map<String, Double> item;

    public DiningMock() {
        item = new HashMap<>();
    }

    @Override
    public Map<String, Double> getDealItemsWithPrice() throws Exception {
        item.put("Common seater", 150.00);
        item.put("Private seater", 300.00);
        item.put("Window seater", 170.00);
        item.put("Lounge seater", 180.00);
        return item;
    }
}

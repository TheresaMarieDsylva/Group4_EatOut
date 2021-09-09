package com.eatOut.deal;

import java.util.HashMap;
import java.util.Map;

public class TakeawayMock extends Deal {
    Map<String, Double> item;

    public TakeawayMock() {
        item = new HashMap<>();
    }

    @Override
    public Map<String, Double> getDealItemsWithPrice() throws Exception {
        item.put("Sea food", 150.00);
        item.put("Japanese Sushi", 300.00);
        item.put("All Canadian", 170.00);
        item.put("Smoked Salmon", 180.00);
        return item;
    }
}

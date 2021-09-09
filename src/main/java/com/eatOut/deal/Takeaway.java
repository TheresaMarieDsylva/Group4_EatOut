package com.eatOut.deal;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Takeaway extends Deal {
    Map<String, Double> item;

    public Takeaway() {
        item = new HashMap<>();
    }

    @Override
    public Map<String, Double> getDealItemsWithPrice() throws Exception {
        IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantsMenuItems()");
        List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
        if (null == tableList) {
            throw new Exception("Error in fetching menu types");
        }
        for (Map<String, Object> row : tableList) {
            item.put(row.get("menu_type").toString(), (Double) row.get("menu_price"));
        }
        return item;
    }
}

package com.eatOut.deal;

import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.StoredProcedure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dining extends Deal {
    Map<String, Double> item;

    public Dining() {
        item = new HashMap<>();
    }

    @Override
    public Map<String, Double> getDealItemsWithPrice() throws Exception {
        try{
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantsTableItems()");
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();

            for (Map<String, Object> row : tableList) {
                item.put(row.get("table_type").toString(), (Double) row.get("table_price"));
            }
        }
        catch (Exception exception){
            throw new Exception("Error in fetching dining items");
        }
        return item;
    }
}

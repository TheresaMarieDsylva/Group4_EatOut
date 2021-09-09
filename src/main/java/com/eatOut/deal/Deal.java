package com.eatOut.deal;

import java.util.Map;

public abstract class Deal {

    public abstract Map<String, Double> getDealItemsWithPrice() throws Exception;
}

package com.eatOut.deal;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;
import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;
import com.eatOut.restaurant.RestaurantDaoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DealOffer implements IDealOffer {
    private String dealId;
    private String dealItem;
    private BookType dealType;
    private String dealEndDate;
    private String restaurantName;
    private String restaurantId;
    private double dealPrice;
    private double oldPrice;
    private IDateCalculator dateCalculator;
    private IDealValidation dealValidation;
    private List<DealOffer> dealOffers;
    private List<Restaurant> restaurants;

    public DealOffer() {
        dealOffers = new ArrayList<>();
        restaurants = new ArrayList<>();
        dateCalculator = new CalendarDateCalculator();
        dealValidation = new DealValidation();
    }

    public String getDealId() { return dealId; }

    public void setDealId(String dealId) { this.dealId = dealId; }

    public String getDealItem() {
        return dealItem;
    }

    public void setDealItem(String dealItem) {
        this.dealItem = dealItem;
    }

    public BookType getDealType() {
        return dealType;
    }

    public void setDealType(BookType dealType) {
        this.dealType = dealType;
    }

    public String getDealEndDate() {
        return dealEndDate;
    }

    public void setDealEndDate(String dealEndDate) {
        this.dealEndDate = dealEndDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int createDeal(IDealDAO dealDAO, String selectedDealType, int restaurantId,
                             String newPrice, String dealItem, String dealOldPrice, String endDate) throws Exception {
        int row = 0;
        Double dealPrice = Double.valueOf(newPrice);
        Double oldPrice = Double.valueOf(dealOldPrice);
        LocalDateTime endDateTime = dateCalculator.parseToLocalDateTime(endDate);
        dealOffers = dealDAO.loadDeals();
        if (dealValidation.checkUserInputForDeal(dealOffers, restaurantId, selectedDealType, dealItem, endDateTime)) {
            Timestamp endDateTimeStamp = Timestamp.valueOf(endDateTime);
            row = dealDAO.addDealOffer(restaurantId, dealPrice, selectedDealType, dealItem, oldPrice, endDateTimeStamp);
        }
        return row;
    }

    @Override
    public List<DealOffer> loadValidDeals(IDealDAO dealDAO) throws Exception {
        dealOffers = new ArrayList<>();
        dealOffers = dealDAO.loadDeals();
        dealOffers = dealValidation.filterExpiredDeals(dealOffers, dateCalculator);
        return dealOffers;
    }

    @Override
    public List<Restaurant> loadRestaurantsByLocation(IRestaurantOperation restaurantService, String location) throws Exception {
        restaurants = restaurantService.getRestaurantDetailsByCountry(location);
        return restaurants;
    }

    @Override
    public List<String> getRestaurantCountries(IRestaurantOperation restaurantService) throws Exception {
        return restaurantService.getRestaurantCountries(new RestaurantDaoImpl());
    }

    @Override
    public Map<String, Double> loadRestaurantItemsByDealType(Deal deal) throws Exception {
        return deal.getDealItemsWithPrice();
    }
}

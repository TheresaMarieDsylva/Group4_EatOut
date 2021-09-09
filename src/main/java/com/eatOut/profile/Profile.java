package com.eatOut.profile;

import java.util.ArrayList;
import java.util.List;

public class Profile implements IProfile{

    String couponName;
    String couponCode;
    String amount;
    String description;
    String customerName;
    String restaurantName;
    String peopleAttended;
    String bookingDate;
    String bookingTime;
    String seatLocation;
    String specialRequests ;
    String itemName;
    String quantity;
    String billAmount;
    String dateAndTime;
    String restaurantType;
    String reviewedDate;
    String ratings;
    String comments;
    ProfileDAO profileDAO = new ProfileDAO();

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouponName(){ return couponName; }

    public String getCouponCode(){
        return couponCode;
    }

    public String getAmount(){
        return amount;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public List<Profile> showCouponHistoryByCustomer(int tempCusID) {
        List<Profile> couponsUsedDB = this.profileDAO.showUsedCouponsByCustomerDB(tempCusID);
        return couponsUsedDB;
    }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setPeopleAttended(String peopleAttended) {
        this.peopleAttended = peopleAttended;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setSeatLocation(String seatLocation) {
        this.seatLocation = seatLocation;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getCustomerName() { return customerName; }

    public String getRestaurantName() { return restaurantName; }

    public String getPeopleAttended() {
        return peopleAttended;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getSeatLocation() {
        return seatLocation;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    @Override
    public List<Profile> showBookingHistoryForCustomer(int tempCusID) {
        List<Profile> bookingHistoryDB = profileDAO.showBookingHistoryForCustomerDB(tempCusID);
        return bookingHistoryDB;
    }

    @Override
    public List<Profile> showBookingHistoryForRestaurant(IProfileDAO profileDAO,int restaurantId) throws Exception {
        List<Profile> bookingHistoryForRestaurantDB = new ArrayList<>();
        try {
            bookingHistoryForRestaurantDB = profileDAO.showBookingHistoryForRestaurantDB(restaurantId);
        } catch (Exception e) {
            throw new Exception("Exception occurred fetching booking history of restaurant");
        }
        return bookingHistoryForRestaurantDB;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getBillAmount() {
        return billAmount;
    }

    @Override
    public List<Profile> showOrderHistoryForCustomer(int tempCusID) {
        List<Profile> orderHistoryDB = profileDAO.showOrderHistoryForCustomerDB(tempCusID);
        return orderHistoryDB;
    }

    @Override
    public List<Profile> showRestaurantOrderHistoryForRestaurant(IProfileDAO profileDAO,int restaurantId) throws Exception {
        List<Profile> orderHistoryForRestaurantDB = new ArrayList<>();
        try {
            orderHistoryForRestaurantDB = profileDAO.showRestaurantOrderHistoryForRestaurantDB(restaurantId);
        } catch (Exception e) {
            throw new Exception("Exception occurred fetching order history of restaurant");
        }
        return orderHistoryForRestaurantDB;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public void setReviewedDate(String reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public String getReviewedDate() {
        return reviewedDate;
    }

    public String getRatings() {
        return ratings;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public List<Profile> showUserReviews(int tempCusID) {
        List<Profile> userReviewsDB = profileDAO.showUserReviewsDB(tempCusID);
        return userReviewsDB;
    }

    @Override
    public List<Profile> showReviewsForRestaurant(IProfileDAO profileDAO, int restaurantId) throws Exception {
        List<Profile> restaurantReviewsDB = new ArrayList<>();
        try {
            restaurantReviewsDB = this.profileDAO.showReviewsForRestaurantDB(restaurantId);
        }catch (Exception e) {
            throw new Exception("Exception occurred fetching reviews of restaurant");
        }
        return restaurantReviewsDB;
    }
}

package com.eatOut.booktable;

public class BookTable {
    BookTableDAO bookTableDAO = new BookTableDAO();
    ValidateBooking validateBooking = new ValidateBooking();
    BookTable bookTable = null;
    public String commonSeats, terraceSeats, windowSeats, loungeSeats, privateSeats;
    public String commonSeatsPrice, terraceSeatsPrice, windowSeatsPrice, loungeSeatsPrice, privateSeatsPrice;

    public Boolean bookTableDB(int tempCusID, int peopleCount, String bookDate, String bookTime, String tableArea, String specialRequests, String tempRestaurantID) {
        Boolean value = false;
        value = bookTableDAO.createBookingEntry(tempCusID,peopleCount,bookDate,bookTime,tableArea,specialRequests, tempRestaurantID);
        return value;
    }

    public void updateTableDetails(String cSeat, String tSeat, String wSeat, String lSeat, String pSeat, String cSeatPrice, String tSeatPrice, String wSeatPrice, String lSeatPrice, String pSeatPrice, int tempResID) {
        bookTableDAO.updateTableDetails(cSeat,tSeat,wSeat,lSeat,pSeat,cSeatPrice,tSeatPrice,wSeatPrice,lSeatPrice,pSeatPrice, tempResID);
    }

    public String getCommonSeatCount() {
        return commonSeats;
    }

    public String getTerraceSeatCount() {
        return terraceSeats;
    }

    public String getWindowSeatCount() {
        return windowSeats;
    }

    public String getLoungeSeatCount() {
        return loungeSeats;
    }

    public String getPrivateSeatCount() {
        return privateSeats;
    }


    public void setCommonSeatCount(String commonSeats) {
        this.commonSeats = commonSeats;
    }

    public void setTerraceSeatCount(String terraceSeats) {
        this.terraceSeats = terraceSeats;
    }

    public void setWindowSeatCount(String windowSeats) {
        this.windowSeats = windowSeats;
    }

    public void setLoungeSeatCount(String loungeSeats) {
        this.loungeSeats = loungeSeats;
    }

    public void setPrivateSeatCount(String privateSeats) {
        this.privateSeats = privateSeats;
    }


    public String getCommonSeatPrice() {
        return commonSeatsPrice;
    }

    public String getTerraceSeatPrice() {
        return terraceSeatsPrice;
    }

    public String getWindowSeatPrice() {
        return windowSeatsPrice;
    }

    public String getLoungeSeatPrice() {
        return loungeSeatsPrice;
    }

    public String getPrivateSeatPrice() {
        return privateSeatsPrice;
    }

    public void setCommonSeatPrice(String commonSeatsPrice) {
        this.commonSeatsPrice = commonSeatsPrice;
    }

    public void setTerraceSeatPrice(String terraceSeatsPrice) {
        this.terraceSeatsPrice = terraceSeatsPrice;
    }

    public void setWindowSeatPrice(String windowSeatsPrice) {
        this.windowSeatsPrice = windowSeatsPrice;
    }

    public void setLoungeSeatPrice(String loungeSeatsPrice) {
        this.loungeSeatsPrice = loungeSeatsPrice;
    }

    public void setPrivateSeatPrice(String privateSeatsPrice) {
        this.privateSeatsPrice = privateSeatsPrice;
    }

    public BookTable showTable(String restaurantID) {
        bookTable = bookTableDAO.getSeatCountPriceDB(restaurantID);
        return bookTable;
    }


    public BookTable timeSeatValidity(String tableArea, String tempRestaurantID, String bookDate, String bookTime) {
        bookTable = validateBooking.timeSeatValidate(tableArea, tempRestaurantID, bookDate, bookTime);
        return bookTable;
    }

}

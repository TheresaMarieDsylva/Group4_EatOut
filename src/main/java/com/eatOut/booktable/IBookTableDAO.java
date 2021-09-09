package com.eatOut.booktable;

public interface IBookTableDAO {

    public BookTable getSeatCountPriceDB(String restaurantID);

    Boolean createBookingEntry(int tempCusID, int peopleCount, String bookDate, String bookTime, String tableArea, String specialRequests, String tempRestaurantID);

    BookTable timeSeatValidateDB(String tableArea, String tempRestaurantID, String bookDate, String bookTime);

    Boolean updateTableDetails(String cSeat, String tSeat, String wSeat, String lSeat, String pSeat, String cSeatPrice, String tSeatPrice, String wSeatPrice, String lSeatPrice, String pSeatPrice, int tempResID);
}

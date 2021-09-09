package com.eatOut.booktable;

public interface IValidateBooking {

     void validateSeatingDetails(String bookTime, String time, String seat);

    BookTable timeSeatValidate(String tableArea, String tempRestaurantID, String bookDate, String bookTime);

    int[] findAvailableSeatsByHourly(int[] originalTableCapacity);

    ValidateBooking.BookTableFlag checkIfDateIsValid(String bookDate);

    ValidateBooking.BookTableFlag checkIfPeopleCountValid(int getPeopleNumber);

}

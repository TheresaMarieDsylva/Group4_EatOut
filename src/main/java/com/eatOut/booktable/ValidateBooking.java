package com.eatOut.booktable;
import java.time.LocalDate;
import java.time.ZoneId;


public class ValidateBooking implements  IValidateBooking{
    BookTableDAO bookTableDAO = new BookTableDAO();
    int i=0,j=0,k=0,l=0,m=0;
    int[] originalTableCapacity = new int[5];
    BookTableFlag bookTableFlag;
    String[] seat_type = new String[5];
    public enum BookTableFlag {
        DATE_NOT_VALID,
        DATE_VALID,
        PEOPLE_COUNT_NOT_VALID,
        PEOPLE_COUNT_VALID
    }
    @Override
    public  void validateSeatingDetails(String bookTime, String time, String seat) {
        String[] userDateArray = bookTime.split(":");
        String userDate = userDateArray[0];
        String[] dbDateArray = time.split(":");
        String  dbDate = dbDateArray[0];
        switch(seat){
            case "common_seats":
                if(dbDate.equals(userDate)) {
                    i++;
                }
                break;
            case "terrace_seats":
                if(dbDate.equals(userDate)) {
                    j++;
                }
                break;
            case "window_seats":
                if(dbDate.equals(userDate)) {
                    k++;
                }
                break;
            case "lounge_seats":
                if(dbDate.equals(userDate)) {
                    l++;
                }
                break;
            case "private_seats":
                if(dbDate.equals(userDate)) {
                    m++;
                }
                break;
        }
    }

    @Override
    public BookTable timeSeatValidate(String tableArea, String tempRestaurantID, String bookDate, String bookTime) {
        BookTable bookTable = new BookTable();
        BookTableDAO bookTableDAO = new BookTableDAO();
        bookTable = bookTableDAO.timeSeatValidateDB(tableArea, tempRestaurantID, bookDate, bookTime);
        return bookTable;
    }
    @Override
    public int[] findAvailableSeatsByHourly(int[] originalTableCapacity) {
        int n =0;
       this.originalTableCapacity[n]=originalTableCapacity[n]-i;
       this.originalTableCapacity[++n]=originalTableCapacity[n]-j;
       this.originalTableCapacity[++n]=originalTableCapacity[n]-k;
       this.originalTableCapacity[++n]=originalTableCapacity[n]-l;
       this.originalTableCapacity[++n]=originalTableCapacity[n]-m;
       return this.originalTableCapacity;
    }


    @Override
    public BookTableFlag checkIfDateIsValid(String bookDate){
        LocalDate today = LocalDate.now( ZoneId.of( "America/Montreal" ) ) ;
        LocalDate bookDateParse = LocalDate.parse(bookDate);
        if(bookDateParse.isAfter(today) || bookDateParse.equals(today)){
            bookTableFlag = BookTableFlag.DATE_VALID;
        }
        else{
            bookTableFlag = BookTableFlag.DATE_NOT_VALID;
        }
        return bookTableFlag;
    }

    @Override
    public BookTableFlag checkIfPeopleCountValid(int getPeopleNumber){
        if(getPeopleNumber<=0){
            bookTableFlag = BookTableFlag.PEOPLE_COUNT_NOT_VALID;
        }
        else{
            bookTableFlag = BookTableFlag.PEOPLE_COUNT_VALID;
        }
        return bookTableFlag;
    }

}

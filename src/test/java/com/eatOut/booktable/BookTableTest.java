package com.eatOut.booktable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class BookTableTest {
    static IValidateBooking validateBooking = new ValidateBooking();
    ValidateBooking.BookTableFlag bookTableFlag ;
    static BookTable bookTable;
    static BookTableDAO bookTableDAO = mock(BookTableDAO.class);


    @Test
    public void validateSeatingDetails() {
        int[] arrayList = new int[5];
        int j=1;
        for(int i =0;i<5;i++){
            arrayList[i]=i;
        }
        validateBooking.validateSeatingDetails("22:10:00","22:12:00","window_seats");
        arrayList = validateBooking.findAvailableSeatsByHourly(arrayList);
        for(int i : arrayList){
            if(i==2) {
                assertEquals(j, arrayList[i]);
            }
        }
        validateBooking.validateSeatingDetails("22:10:00","23:12:00","window_seats");
        arrayList = validateBooking.findAvailableSeatsByHourly(arrayList);
        for(int i : arrayList){
            if(i==2) {
                assertNotEquals(j, arrayList[i]);
            }
        }
    }

    @Test
    public void findAvailableSeatsByHourly() {
        int[] arrayList = new int[5];
        int j=0;
        for(int i =0;i<5;i++){
            arrayList[i]=i;
        }
        arrayList = validateBooking.findAvailableSeatsByHourly(arrayList);
        for(int i : arrayList){
            assertEquals(j,arrayList[i]);
            j++;
        }
    }

    @Test
    public void checkIfDateIsValid() {
        bookTableFlag = validateBooking.checkIfDateIsValid("2021-03-27");
        assertNotEquals(bookTableFlag.DATE_VALID,bookTableFlag);
        bookTableFlag=validateBooking.checkIfDateIsValid("2021-03-27");
        assertEquals(bookTableFlag.DATE_NOT_VALID,bookTableFlag);

    }

    @Test
    public void checkIfPeopleCountValid() {
        bookTableFlag=validateBooking.checkIfPeopleCountValid(0);
        assertEquals(bookTableFlag.PEOPLE_COUNT_NOT_VALID,bookTableFlag);
        bookTableFlag = validateBooking.checkIfPeopleCountValid(5);
        assertEquals(bookTableFlag.PEOPLE_COUNT_VALID,bookTableFlag);
        bookTableFlag=validateBooking.checkIfPeopleCountValid(-1);
        assertEquals(bookTableFlag.PEOPLE_COUNT_NOT_VALID,bookTableFlag);
    }

    @Test
    public void getSeatCountPriceDB() throws Exception {
        when(bookTableDAO.getSeatCountPriceDB("1")).thenReturn(new BookTable());
        bookTable = bookTableDAO.getSeatCountPriceDB("1");
        assertEquals(bookTable, bookTableDAO.getSeatCountPriceDB("1"));
    }

    @Test
    public void createBookingEntry(){
        when(bookTableDAO.createBookingEntry(1,2,"2021-03-29","14:15:00", "window_seats","none","2")).thenReturn(true);
        assertEquals(true,bookTableDAO.createBookingEntry(1,2,"2021-03-29","14:15:00", "window_seats","none","2"));
    }

    @Test
    public void timeSeatValidateDB(){
        when(bookTableDAO.timeSeatValidateDB("common_seats","2","2021-03-29","20:00:00")).thenReturn(new BookTable());
        bookTable = bookTableDAO.timeSeatValidateDB("common_seats","2","2021-03-29","20:00:00");
        assertEquals(bookTable,bookTableDAO.timeSeatValidateDB("common_seats","2","2021-03-29","20:00:00"));
    }

    @Test
    public void updateTableDetailsTest() {
        when(bookTableDAO.updateTableDetails("1", "2", "3", "4", "5", "100", "200", "300", "400", "500", 100)).thenReturn(true);
        Assert.assertEquals(true, bookTableDAO.updateTableDetails("1", "2", "3", "4", "5", "100", "200", "300", "400", "500", 100));
        when(bookTableDAO.updateTableDetails("1", "2", "3", "4", "5", "100", "200", "300", "400", "500", 1000)).thenReturn(false);
        Assert.assertEquals(false, bookTableDAO.updateTableDetails("1", "2", "3", "4", "5", "100", "200", "300", "400", "500", 1000));
    }


    @AfterClass
    public static void tearDown() {
        validateBooking = null;
        bookTableDAO = null;
        bookTable = null;
    }
}
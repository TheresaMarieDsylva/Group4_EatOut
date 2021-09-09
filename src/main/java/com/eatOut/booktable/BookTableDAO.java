package com.eatOut.booktable;

import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.MySQLConnection;
import com.eatOut.database.StoredProcedure;

import java.sql.*;

public class BookTableDAO implements IBookTableDAO{
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;

    @Override
    public BookTable getSeatCountPriceDB(String tempRestaurantID) {
        BookTable bookTable = null;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String seatCount = "SELECT * FROM booking_additional_details WHERE restaurant_id = '"+tempRestaurantID+"' ";
            preparedStatement = connection.prepareStatement(seatCount);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bookTable = new BookTable();
                bookTable.setCommonSeatCount(resultSet.getString("common_seats"));
                bookTable.setTerraceSeatCount(resultSet.getString("terrace_seats"));
                bookTable.setWindowSeatCount(resultSet.getString("window_seats"));
                bookTable.setLoungeSeatCount(resultSet.getString("lounge_seats"));
                bookTable.setPrivateSeatCount(resultSet.getString("private_seats"));
                bookTable.setCommonSeatPrice(resultSet.getString("common_seats_price"));
                bookTable.setTerraceSeatPrice(resultSet.getString("terrace_seats_price"));
                bookTable.setWindowSeatPrice(resultSet.getString("window_seats_price"));
                bookTable.setLoungeSeatPrice(resultSet.getString("lounge_seats_price"));
                bookTable.setPrivateSeatPrice(resultSet.getString("private_seats_price"));

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return bookTable;
    }


    @Override
    public Boolean createBookingEntry(int tempCusID, int peopleCount, String bookDate, String bookTime, String tableArea, String specialRequests, String tempRestaurantID) {
        Boolean value = false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String createBookingEntry = "INSERT INTO table_booking VALUES ( default , '"+tempCusID+"','"+tempRestaurantID+"','"+peopleCount+"','"+ bookDate +"','"+bookTime +"','"+tableArea+"','"+specialRequests+"')";
            statement = connection.createStatement();
            int count = statement.executeUpdate(createBookingEntry);
            if(count>=1){
                value = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return value;
    }

    private void closeConnection(){
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BookTable timeSeatValidateDB(String tableArea, String tempRestaurantID, String bookDate, String bookTime) {
        BookTable bookTable = new BookTable();
        ValidateBooking validateBooking = new ValidateBooking();
        int[] originalTableCapacity = new int[5];
        int n=0;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String dateBooked = "SELECT * FROM table_booking WHERE (b_date = '"+bookDate+"' and restaurant_id='"+tempRestaurantID+"')";
            preparedStatement = connection.prepareStatement(dateBooked);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
            String time = resultSet.getString("b_time");
            String seat = resultSet.getString("seat_loc");
            validateBooking.validateSeatingDetails(bookTime,time,seat);
            }

            String originalTableCapacityDB = "SELECT * FROM booking_additional_details where restaurant_id = '"+tempRestaurantID+"' ";
            preparedStatement = connection.prepareStatement(originalTableCapacityDB);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                originalTableCapacity[n] = Integer.parseInt(resultSet.getString("common_seats"));
                originalTableCapacity[++n] = Integer.parseInt(resultSet.getString("terrace_seats"));
                originalTableCapacity[++n] = Integer.parseInt(resultSet.getString("window_seats"));
                originalTableCapacity[++n] = Integer.parseInt(resultSet.getString("lounge_seats"));
                originalTableCapacity[++n] = Integer.parseInt(resultSet.getString("private_seats"));
                bookTable.setCommonSeatPrice(resultSet.getString("common_seats_price"));
                bookTable.setTerraceSeatPrice(resultSet.getString("terrace_seats_price"));
                bookTable.setWindowSeatPrice(resultSet.getString("window_seats_price"));
                bookTable.setLoungeSeatPrice(resultSet.getString("lounge_seats_price"));
                bookTable.setPrivateSeatPrice(resultSet.getString("private_seats_price"));
            }
            originalTableCapacity = validateBooking.findAvailableSeatsByHourly(originalTableCapacity);
            int i =0;
            bookTable.setCommonSeatCount(Integer.toString(originalTableCapacity[i]));
            bookTable.setTerraceSeatCount(Integer.toString(originalTableCapacity[++i]));
            bookTable.setWindowSeatCount(Integer.toString(originalTableCapacity[++i]));
            bookTable.setLoungeSeatCount(Integer.toString(originalTableCapacity[++i]));
            bookTable.setPrivateSeatCount(Integer.toString(originalTableCapacity[++i]));


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return bookTable;
    }

    @Override
    public Boolean updateTableDetails(String cSeat, String tSeat, String wSeat, String lSeat, String pSeat, String cSeatPrice, String tSeatPrice, String wSeatPrice, String lSeatPrice, String pSeatPrice, int tempResID) {
        Boolean value = false;

        StoredProcedure storedProcedure = null;
        try {
            storedProcedure = new StoredProcedure("updateTableValues(?,?,?,?,?,?,?,?,?,?,?)");
            storedProcedure.setParameter(1, cSeat);
            storedProcedure.setParameter(2, tSeat);
            storedProcedure.setParameter(3, wSeat);
            storedProcedure.setParameter(4, lSeat);
            storedProcedure.setParameter(5, pSeat);
            storedProcedure.setParameter(6, cSeatPrice);
            storedProcedure.setParameter(7, tSeatPrice);
            storedProcedure.setParameter(8, wSeatPrice);
            storedProcedure.setParameter(9, lSeatPrice);
            storedProcedure.setParameter(10, pSeatPrice);
            storedProcedure.setParameter(11, tempResID);
            int resultSet = storedProcedure.executeWithResult();
            if(resultSet==0){
                value = false;
            }
            else{
                value = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

}

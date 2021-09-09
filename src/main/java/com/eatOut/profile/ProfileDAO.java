package com.eatOut.profile;

import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.MySQLConnection;
import com.eatOut.database.StoredProcedure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class ProfileDAO implements IProfileDAO {
    private static Logger logger;
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public List<Profile> showUsedCouponsByCustomerDB(int tempCudID) {
        List<Profile> list=new ArrayList<>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String couponHistory = "select * from coupon inner join coupon_history on coupon.coupon_id=coupon_history.coupon_id where coupon_history.cus_id='"+tempCudID+"'";
            preparedStatement = connection.prepareStatement(couponHistory);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile customerProfile = new Profile();
                customerProfile.setCouponName(resultSet.getString("coupon_name"));
                customerProfile.setCouponCode(resultSet.getString("coupon_code"));
                customerProfile.setAmount(resultSet.getString("amount"));
                customerProfile.setDescription(resultSet.getString("description"));
                list.add(customerProfile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Profile> showBookingHistoryForCustomerDB(int tempCudID) {
        List<Profile> list=new ArrayList<>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String bookingHistory = "select booking_additional_details.restaurant_name,table_booking.num_of_people, table_booking.b_date,table_booking.b_time,table_booking.seat_loc, table_booking.spec_requests from booking_additional_details inner join table_booking on booking_additional_details.restaurant_id=table_booking.restaurant_id where table_booking.customer_id='"+tempCudID+"'";
            preparedStatement = connection.prepareStatement(bookingHistory);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile customerProfile = new Profile();
                customerProfile.setRestaurantName(resultSet.getString("restaurant_name"));
                customerProfile.setPeopleAttended(resultSet.getString("num_of_people"));
                customerProfile.setBookingDate(resultSet.getString("b_date"));
                customerProfile.setBookingTime(resultSet.getString("b_time"));
                customerProfile.setSeatLocation(resultSet.getString("seat_loc"));
                customerProfile.setSpecialRequests(resultSet.getString("spec_requests"));
                list.add(customerProfile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Profile> showBookingHistoryForRestaurantDB(int restaurantId) throws Exception {
        List<Profile> list=new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantBookingHistory(?)");
            storedProcedure.setParameter(1, restaurantId);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
                for (Map<String, Object> row : tableList) {
                    Profile restaurantProfile = new Profile();
                    restaurantProfile.setCustomerName(row.get("first_name").toString());
                    restaurantProfile.setPeopleAttended(row.get("num_of_people").toString());
                    restaurantProfile.setBookingDate(row.get("b_date").toString());
                    restaurantProfile.setBookingTime(row.get("b_time").toString());
                    restaurantProfile.setSeatLocation(row.get("seat_loc").toString());
                    restaurantProfile.setSpecialRequests(row.get("spec_requests").toString());
                    list.add(restaurantProfile);
                }
        }catch (Exception ex) {
            throw new Exception("Not able to fetch booking history for restaurant");
        }
        return list;
    }

    @Override
    public List<Profile> showOrderHistoryForCustomerDB(int tempCudID) {
        List<Profile> list=new ArrayList<>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String orderHistory = "select restaurant.restaurant_name,takeaway_orders.item_name, takeaway_orders.quantity,takeaway_orders.order_timestamp,takeaway_orders.order_bill_amount from restaurant inner join takeaway_orders on restaurant.restaurant_id=takeaway_orders.restaurant_id where takeaway_orders.customer_id='"+tempCudID+"'";
            preparedStatement = connection.prepareStatement(orderHistory);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile customerProfile = new Profile();
                customerProfile.setRestaurantName(resultSet.getString("restaurant_name"));
                customerProfile.setItemName(resultSet.getString("item_name"));
                customerProfile.setQuantity(resultSet.getString("quantity"));
                customerProfile.setDateAndTime(resultSet.getString("order_timestamp"));
                customerProfile.setBillAmount(resultSet.getString("order_bill_amount"));
                list.add(customerProfile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Profile> showRestaurantOrderHistoryForRestaurantDB(int restaurantId) throws Exception {
        List<Profile> list=new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantOrderHistory(?)");
            storedProcedure.setParameter(1, restaurantId);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            if (tableList.isEmpty()) {
                logger.debug("No additional details found for the restaurant");
            } else {
                for (Map<String, Object> row : tableList) {
                    Profile restaurantProfile = new Profile();
                    restaurantProfile.setCustomerName(row.get("first_name").toString());
                    restaurantProfile.setItemName(row.get("item_name").toString());
                    restaurantProfile.setQuantity(row.get("quantity").toString());
                    restaurantProfile.setDateAndTime(row.get("order_timestamp").toString());
                    restaurantProfile.setBillAmount(row.get("order_bill_amount").toString());
                    list.add(restaurantProfile);
                }
            }
        }catch (Exception ex) {
            throw new Exception("Not able to fetch order history for restaurant");
        }
        return list;
    }

    @Override
    public List<Profile> showUserReviewsDB(int tempCusID) {
        List<Profile> list=new ArrayList<>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String userReviews = "SELECT * FROM ratings_and_reviews where customer_id ='"+tempCusID+"'";
            preparedStatement = connection.prepareStatement(userReviews);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile customerProfile = new Profile();
                customerProfile.setRestaurantName(resultSet.getString("restaurant_name"));
                customerProfile.setItemName(resultSet.getString("item_name"));
                customerProfile.setRestaurantType(resultSet.getString("restaurant_type"));
                customerProfile.setReviewedDate(resultSet.getString("reviewed_date"));
                customerProfile.setRatings(resultSet.getString("ratings"));
                customerProfile.setComments(resultSet.getString("comments"));
                list.add(customerProfile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Profile> showReviewsForRestaurantDB(int restaurantId) throws Exception {
        List<Profile> list=new ArrayList<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("getRestaurantReviews(?)");
            storedProcedure.setParameter(1, restaurantId);
            List<Map<String, Object>> tableList = storedProcedure.executeAndFetchTable();
            if (tableList.isEmpty()) {
                logger.debug("No additional details found for the restaurant");
            } else {
                for (Map<String, Object> row : tableList) {
                    Profile restaurantProfile = new Profile();
                    restaurantProfile.setReviewedDate(row.get("reviewed_date").toString());
                    restaurantProfile.setRatings(row.get("ratings").toString());
                    restaurantProfile.setComments(row.get("comments").toString());
                    list.add(restaurantProfile);
                }
            }
        }catch (Exception ex) {
            throw new Exception("Not able to fetch reviews for restaurant");
        }
        return list;
    }

}

package com.eatOut.profile;

import java.util.List;

public interface IProfileDAO {

    List<Profile> showUsedCouponsByCustomerDB(int customerId);
    List<Profile> showBookingHistoryForCustomerDB(int customerId);
    List<Profile> showBookingHistoryForRestaurantDB(int restaurantId) throws Exception;
    List<Profile> showOrderHistoryForCustomerDB(int customerId);
    List<Profile> showRestaurantOrderHistoryForRestaurantDB(int restaurantId) throws Exception;
    List<Profile> showUserReviewsDB(int customerId);
    List<Profile> showReviewsForRestaurantDB(int restaurantId) throws Exception;
}

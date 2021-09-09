package com.eatOut.profile;

import java.util.List;

public interface IProfile {
     List<Profile> showCouponHistoryByCustomer(int customerId);
     List<Profile> showBookingHistoryForCustomer(int customerId);
     List<Profile> showBookingHistoryForRestaurant(IProfileDAO profileDAO,int restaurantId) throws Exception;
     List<Profile> showOrderHistoryForCustomer(int customerId);
     List<Profile> showRestaurantOrderHistoryForRestaurant(IProfileDAO profileDAO,int restaurantId) throws Exception;
     List<Profile> showUserReviews(int customerId);
     List<Profile> showReviewsForRestaurant(IProfileDAO profileDAO,int restaurantId) throws Exception;
}

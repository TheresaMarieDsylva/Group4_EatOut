package com.eatOut.restaurant;

public class Reviews {
	
	private int reviewId;
	private int restaurantId;
	private int customerId;
	private String customerName;
	private String comments;
	private double ratingsValue;

	
	protected void addRatingsAndReviews(IRestaurantAddtnlDtls restaurantAddtnlDtls) {
		restaurantAddtnlDtls.addReviewsFrRestaurant(this);
	}
	
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getRatingsValue() {
		return ratingsValue;
	}
	public void setRatingsValue(double ratingsValue) {
		this.ratingsValue = ratingsValue;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	@Override
	public String toString() {
		return "Reviews [reviewId=" + reviewId + ", restaurantId=" + restaurantId + ", customerId=" + customerId
				+ ", customerName=" + customerName + ", comments=" + comments + ", ratingsValue=" + ratingsValue + "]";
	}
	
	

}

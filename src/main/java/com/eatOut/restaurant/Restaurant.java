package com.eatOut.restaurant;

import java.util.Date;
import java.util.List;

public class Restaurant {

	private int restaurantId;
	private String restaurantName;
	private long phoneNumber;
	private String address;
	private String city;
	private String country;
	private String email;
	private String password;
	private Date createdAt;
	private String status;
	private double overallRatings;
	private RestaurantAdditionalDtls restaurantAdditionalDtls;
	private List<Reviews> reviewlist;
	
	
	protected void loadRestaurantAdditionalDtls(IRestaurantAddtnlDtls restaurantAddtnlDtls) {
		restaurantAdditionalDtls=restaurantAddtnlDtls.getRestaurantAddtnlDtls(restaurantId); 
	}
	
	protected void loadReviewList(IRestaurantAddtnlDtls restaurantAddtnlDtls) {
		reviewlist=restaurantAddtnlDtls.getReviewsForRestaurant(restaurantId);
	}
	
	protected void loadOverAllRatings() {
		overallRatings=Math.round((reviewlist.stream().mapToDouble((x) -> x.getRatingsValue()).average()).orElse(0.0));
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getOverallRatings() {
		return overallRatings;
	}

	public void setOverallRatings(double overallRatings) {
		this.overallRatings = overallRatings;
	}

	public RestaurantAdditionalDtls getRestaurantAdditionalDtls() {
		return restaurantAdditionalDtls;
	}

	public void setRestaurantAdditionalDtls(RestaurantAdditionalDtls restaurantAdditionalDtls) {
		this.restaurantAdditionalDtls = restaurantAdditionalDtls;
	}

	public List<Reviews> getReviewlist() {
		return reviewlist;
	}

	public void setReviewlist(List<Reviews> reviewlist) {
		this.reviewlist = reviewlist;
	}

	@Override
	public String toString() {
		return "Restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName + ", phoneNumber="
				+ phoneNumber + ", address=" + address + ", city=" + city + ", country=" + country + ", email=" + email
				+ ", password=" + password + ", createdAt=" + createdAt + ", status=" + status + ", overallRatings="
				+ overallRatings + ", restaurantAdditionalDtls=" + restaurantAdditionalDtls + ", reviewlist="
				+ reviewlist + "]";
	}

	
	
}

package com.eatOut.restaurant;

import java.sql.Time;

import org.springframework.util.StringUtils;

public class RestaurantAdditionalDtls {
	
	private int restaurantId;
	private boolean diningOption;
	private boolean takeAwayOption;
	private boolean commonSeatsOption;
	private boolean terraceSeatsOption;
	private boolean windowSeatsOption;
	private boolean loungeSeatsOption;
	private boolean privateSeatsOption;
	private String cuisineType;
	private Time openingTime;
	private Time closingTime;
	private String strOpeningTime;
	private String strClosingTime;
	private String additionalComments;
	private String approxPrice;
	private boolean bookingFlag;
	private boolean seatingFlag;
	IRestaurantAddtnlDtls restaurantAddtnlDtls;
	
	
	protected void addRestaurantAddtnlDtls(IRestaurantAddtnlDtls restaurantAddtnlDtls) {
		setOpeningAndClosingTime();
		restaurantAddtnlDtls.addRestaurantAddtnlDtls(this);
	}

	private void setOpeningAndClosingTime() {
		if(StringUtils.hasText(strOpeningTime)) {
			openingTime=Time.valueOf(strOpeningTime);
		}
		if(StringUtils.hasText(strClosingTime)) {
			closingTime=Time.valueOf(strClosingTime);
		}
	}

	@Override
	public String toString() {
		return "RestaurantAdditionalDtls [restaurantId=" + restaurantId + ", diningOption=" + diningOption
				+ ", takeAwayOption=" + takeAwayOption + ", commonSeatsOption=" + commonSeatsOption
				+ ", terraceSeatsOption=" + terraceSeatsOption + ", windowSeatsOption=" + windowSeatsOption
				+ ", loungeSeatsOption=" + loungeSeatsOption + ", privateSeatsOption=" + privateSeatsOption
				+ ", cuisineType=" + cuisineType + ", openingTime=" + openingTime + ", closingTime=" + closingTime
				+ ", strOpeningTime=" + strOpeningTime + ", strClosingTime=" + strClosingTime + ", additionalComments="
				+ additionalComments + ", approxPrice=" + approxPrice + ", bookingFlag=" + bookingFlag
				+ ", seatingFlag=" + seatingFlag + "]";
	}


	public boolean isDiningOption() {
		return diningOption;
	}


	public void setDiningOption(boolean diningOption) {
		this.diningOption = diningOption;
	}


	public boolean isTakeAwayOption() {
		return takeAwayOption;
	}


	public void setTakeAwayOption(boolean takeAwayOption) {
		this.takeAwayOption = takeAwayOption;
	}


	public boolean isCommonSeatsOption() {
		return commonSeatsOption;
	}


	public void setCommonSeatsOption(boolean commonSeatsOption) {
		this.commonSeatsOption = commonSeatsOption;
	}


	public boolean isTerraceSeatsOption() {
		return terraceSeatsOption;
	}


	public void setTerraceSeatsOption(boolean terraceSeatsOption) {
		this.terraceSeatsOption = terraceSeatsOption;
	}


	public boolean isWindowSeatsOption() {
		return windowSeatsOption;
	}


	public void setWindowSeatsOption(boolean windowSeatsOption) {
		this.windowSeatsOption = windowSeatsOption;
	}


	public boolean isLoungeSeatsOption() {
		return loungeSeatsOption;
	}


	public void setLoungeSeatsOption(boolean loungeSeatsOption) {
		this.loungeSeatsOption = loungeSeatsOption;
	}


	public boolean isPrivateSeatsOption() {
		return privateSeatsOption;
	}


	public void setPrivateSeatsOption(boolean privateSeatsOption) {
		this.privateSeatsOption = privateSeatsOption;
	}


	public String getCuisineType() {
		return cuisineType;
	}


	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}


	public Time getOpeningTime() {
		return openingTime;
	}


	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}


	public Time getClosingTime() {
		return closingTime;
	}


	public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}


	public String getAdditionalComments() {
		return additionalComments;
	}


	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getApproxPrice() {
		return approxPrice;
	}

	public void setApproxPrice(String approxPrice) {
		this.approxPrice = approxPrice;
	}


	public String getStrOpeningTime() {
		return strOpeningTime;
	}


	public void setStrOpeningTime(String strOpeningTime) {
		this.strOpeningTime = strOpeningTime;
	}


	public String getStrClosingTime() {
		return strClosingTime;
	}


	public void setStrClosingTime(String strClosingTime) {
		this.strClosingTime = strClosingTime;
	}

	public boolean isBookingFlag() {
		return bookingFlag;
	}

	public void setBookingFlag(boolean bookingFlag) {
		this.bookingFlag = bookingFlag;
	}

	public boolean isSeatingFlag() {
		return seatingFlag;
	}

	public void setSeatingFlag(boolean seatingFlag) {
		this.seatingFlag = seatingFlag;
	}
	

	

	
}
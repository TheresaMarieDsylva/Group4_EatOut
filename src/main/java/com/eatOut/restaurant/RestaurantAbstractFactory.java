package com.eatOut.restaurant;

public abstract class RestaurantAbstractFactory {

	public abstract IRestaurantAddtnlDao getRestuarantAddtnlDAO();
	
	public abstract IRestaurantAddtnlDtls getRestaurantAddtnlDtls(IRestaurantAddtnlDao restaurantAddtnlDao);
	
	public abstract IRestaurantDao getRestaurantDAO();
	
	public abstract IRestaurantOperation getRestaurantOperation(IRestaurantAddtnlDtls restaurantAddtnlDtls,IRestaurantDao restaurantDao);

	public abstract IRestaurantOperation getRestaurantOperation(IRestaurantDao restaurantDao);
	
	
}

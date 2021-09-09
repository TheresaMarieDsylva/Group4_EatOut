package com.eatOut.userhome;

import com.eatOut.restaurant.IRestaurantOperation;

public abstract class CustomerAbstractFactory {
	
	public abstract ICustomerDao getCustomerDAO();
	
	public abstract ICustomerOperations getCustomerOperations(IRestaurantOperation restaurantOperation,ICustomerDao customerDao );

}

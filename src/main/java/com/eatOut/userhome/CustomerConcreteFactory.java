package com.eatOut.userhome;

import com.eatOut.restaurant.IRestaurantOperation;

public class CustomerConcreteFactory extends CustomerAbstractFactory{


	@Override
	public ICustomerDao getCustomerDAO() {
		return new CustomerDaoImpl();
	}

	@Override
	public ICustomerOperations getCustomerOperations(IRestaurantOperation restaurantOperation,ICustomerDao customerDao) {
		return new CustomerOperationsImpl(restaurantOperation,customerDao);
	}
}

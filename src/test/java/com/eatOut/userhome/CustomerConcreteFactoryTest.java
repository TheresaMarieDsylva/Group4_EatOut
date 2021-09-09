package com.eatOut.userhome;

import com.eatOut.restaurant.IRestaurantOperation;

public class CustomerConcreteFactoryTest extends CustomerAbstractFactory{

	@Override
	public ICustomerDao getCustomerDAO() {
		return new CustomerDaoImplMock();
	}

	@Override
	public ICustomerOperations getCustomerOperations(IRestaurantOperation restaurantOperation,
			ICustomerDao customerDao) {
		return new CustomerOperationsImpl(restaurantOperation, customerDao);
	}

}

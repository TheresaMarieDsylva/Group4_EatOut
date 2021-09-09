package com.eatOut.restaurant;

public class RestaurantConcreteFactory extends RestaurantAbstractFactory{

	@Override
	public IRestaurantAddtnlDao getRestuarantAddtnlDAO() {
		return new RestaurantAddtnlDaoImpl();
	}

	@Override
	public IRestaurantAddtnlDtls getRestaurantAddtnlDtls(IRestaurantAddtnlDao restaurantAddtnlDao) {
		return new RestaurantAddtnalDtlsImpl(restaurantAddtnlDao);
	}

	@Override
	public IRestaurantDao getRestaurantDAO() {
		return new RestaurantDaoImpl();
	}

	@Override
	public IRestaurantOperation getRestaurantOperation(IRestaurantAddtnlDtls restaurantAddtnlDtls, IRestaurantDao restaurantDao) {
		return new RestaurantOperationsImpl(restaurantAddtnlDtls,restaurantDao);
	}

	@Override
	public IRestaurantOperation getRestaurantOperation(IRestaurantDao restaurantDao) {
		return new RestaurantOperationsImpl(restaurantDao);
	}

}

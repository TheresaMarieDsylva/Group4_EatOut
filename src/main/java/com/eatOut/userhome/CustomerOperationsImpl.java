package com.eatOut.userhome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.eatOut.deal.DealOffer;
import com.eatOut.deal.IDealDAO;
import com.eatOut.deal.IDealOffer;
import com.eatOut.restaurant.IRestaurantOperation;
import com.eatOut.restaurant.Restaurant;

public class CustomerOperationsImpl implements ICustomerOperations{
	
	private static Logger logger = LogManager.getLogger(CustomerOperationsImpl.class);
	
	private static final double MATH_EXPONENT=2;
	private static final double MAX_RATINGS=4.0;
	
	IRestaurantOperation restaurantService;
	ICustomerDao customerDao;

	public CustomerOperationsImpl(IRestaurantOperation restaurantService, ICustomerDao customerDao) {
		this.restaurantService = restaurantService;
		this.customerDao = customerDao;
	}

	@Override
	public List<Restaurant> getRecommendedListFrUsr(int userId) {
		List<Restaurant> restaurantList=new ArrayList<>();	
			try {
				Map<Integer,Double> userMap=getRestrntsBookedByCust(userId);
				String restaurantIds="";
				for(Entry<Integer,Double> entryObject:userMap.entrySet()) {
					restaurantIds+=entryObject.getKey()+",";
				}
				
				if(StringUtils.hasText(restaurantIds)) {
					restaurantIds=restaurantIds.substring(0,restaurantIds.length()-1);
					Map<Integer,Map<Integer,Double>> usersMapFrRestaurants=getUsersFrRestrnts(restaurantIds);
					usersMapFrRestaurants.remove(userId);
					
					int xFactor=0;
					Integer similarUser=0;
					Map<Integer,Double> similarUsersMap=new HashMap<>();
					
					for(Entry<Integer,Double> entrytmp:userMap.entrySet()) {
						xFactor+=Math.pow(entrytmp.getValue(), MATH_EXPONENT);
					}
					double xRoot= Math.sqrt(xFactor);
					
					for(Entry<Integer, Map<Integer, Double>> entryObj:usersMapFrRestaurants.entrySet()) {
						Map<Integer, Double> similarUsrMap=entryObj.getValue();
						Set<Integer> otherUsrRestSet=similarUsrMap.keySet();
						int dotProduct=0;
						int yFactor=0;
						for(int temp:otherUsrRestSet) {
							dotProduct+=userMap.get(temp)*similarUsrMap.get(temp);
							yFactor+=Math.pow(similarUsrMap.get(temp), MATH_EXPONENT);
						}
						Double yRoot= Math.sqrt(yFactor);
						Double similarityIndex= dotProduct / (xRoot * yRoot);
						similarUsersMap.put(entryObj.getKey(), (similarityIndex));
					}
					
					Double maxValue=Collections.max(similarUsersMap.values());
					
					for(Entry<Integer,Double> entry : similarUsersMap.entrySet()) {	
						   if(entry.getValue().equals(maxValue)) {
							   similarUser=entry.getKey();
							   break;
						   }
					 }
					Map<Integer, Double> usrsRatedRestaurant=getRestaurantsRatedByUsr(similarUser,MAX_RATINGS);
					getRestaurantLst(restaurantList, usrsRatedRestaurant);
				}
				restaurantList.removeAll(Collections.singleton(null));
				if(restaurantList.isEmpty()) {
					Map<Integer, Double> highestRatedRestrnts=getHighestRatedRestrntFrUsr(userId);
					getRestaurantLst(restaurantList, highestRatedRestrnts);
				}
			} catch (Exception e) {
				logger.error("Exception occured while fetching recommended list for user",e);
			}
		
		return restaurantList;
	}


	private void getRestaurantLst(List<Restaurant> restaurantList, Map<Integer, Double> highestRatedRestrnts) {
		for(Entry<Integer,Double> restaurantEntry: highestRatedRestrnts.entrySet()) {
			restaurantList.add(restaurantService.getRestaurantDtlsByRestId(restaurantEntry.getKey()));
		}
	}


	@Override
	public Map<Integer, Map<Integer, Double>> getUsersFrRestrnts(String restaurantIds) {
		Map<Integer, Map<Integer, Double>> usersMapFrRestaurants= new HashMap<>();
		try {
			usersMapFrRestaurants=customerDao.getUsersFrRestrnts(restaurantIds);
		} catch (Exception e) {
			logger.info("Exception occured while fetching users for restaurants",e);
		}
		return usersMapFrRestaurants;
	}

	@Override
	public Map<Integer, Double> getRestrntsBookedByCust(int userId) {
		Map<Integer, Double> restaurantBookingsMap=new HashMap<>();
		try {
			restaurantBookingsMap=customerDao.getRestrntsBookedByCust(userId);
		} catch (Exception e) {
			logger.info("Exception occured while fetching restaurants booked by user",e);
		}
		return restaurantBookingsMap;
	}
	
	
	@Override
	public Map<Integer, Double> getRestaurantsRatedByUsr(Integer userId, Double maxRatingsValue) {
		Map<Integer, Double> usrsRatedRestaurant= new HashMap<>();
		try {
			usrsRatedRestaurant=customerDao.getRestaurantsRatedByUsr(userId,maxRatingsValue);
		} catch (Exception e) {
			logger.info("Exception occured while fetching restaurants rated by user",e);
		}
		return usrsRatedRestaurant;
	}
	
	@Override
	public Map<Integer, Double> getHighestRatedRestrntFrUsr(int userId) {
		Map<Integer, Double> highestRatedRestrnts= new HashMap<>();
		try {
			highestRatedRestrnts=customerDao.getHighestRatedRestrntFrUsr(userId);
		} catch (Exception e) {
			logger.info("Exception occured while fetching highest rated restaurants",e);
		}
		return highestRatedRestrnts;
	}

	@Override
	public List<String> loadRestaurantByDeals(IDealOffer dealOffer, IDealDAO dealDAO)  {
		List<String> dealRestaurants=new ArrayList<>();
		try {
			List<DealOffer> dealOffers = dealOffer.loadValidDeals(dealDAO);
			Set<String> dealRestaurantSet = new HashSet<>();
			dealRestaurants = new ArrayList<>();
			dealOffers.forEach(dOffer -> {
				dealRestaurantSet.add(dOffer.getRestaurantName());
			});
			for(String dealRestaurant : dealRestaurantSet) {
				dealRestaurants.add(dealRestaurant);
			}
		} catch (Exception e) {
			logger.info("Exception while fetching deals",e);
		}
		return dealRestaurants;
	}

	@Override
	public int applyDeals(String dealId, String price, String restaurantId, String customerId) {
		int applyDeals=0;
		try {
			List<String> userDealIds = viewDealsByUser(customerId);
			if (checkIfDealRepeated(userDealIds, dealId)) {
				applyDeals= -1;
			}
			else {
				applyDeals= customerDao.applyDeal(dealId, restaurantId, customerId);
			}
		} catch (Exception e) {
			logger.info("Exception occured while applying deals",e);
		}
		
		return applyDeals;
	}

	@Override
	public List<String> viewDealsByUser(String customerId) {
		List<String> dealIds= new ArrayList<>();
		try {
			dealIds = customerDao.viewDealsByUser(customerId);
		} catch (Exception e) {
			logger.info("Exception occured while fetching deals",e);
		}
		return dealIds;
	}

	@Override
	public int calculateCustomerWallet(int customerId) {
		int wallet = 0;
		try {
			List<Integer> totalCouponAmount = customerDao.loadCouponCustomerWallet(customerId);
			int amount = customerDao.loadBalanceCustomerWallet(customerId);
			wallet = totalCouponAmount.stream().mapToInt(Integer::intValue).sum() - amount;

		} catch (Exception e) {
			logger.info("Exception occurred while fetching wallet",e);
		}
		return wallet;
	}

	public boolean checkIfDealRepeated(List<String> userDealIds, String dealId) {
		boolean dealRepeated=false;
		try {
			dealRepeated=userDealIds.contains(dealId);
		} catch (Exception e) {
			logger.info("Exception occured while checking repeated deals",e);
		}
		return dealRepeated;
	}
}

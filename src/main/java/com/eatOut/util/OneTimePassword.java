package com.eatOut.util;

import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OneTimePassword {

	private static Logger logger = LogManager.getLogger(OneTimePassword.class);
	
	public long generate() {
		long rndmNm=0L;
		try {
			SecureRandom randomNum = new SecureRandom();
			rndmNm = randomNum.nextInt(999999) + 100000;
		} catch (Exception e) {
			logger.error("Exception occured whhile creating OTP",e);
		}
		return rndmNm;
	}
}

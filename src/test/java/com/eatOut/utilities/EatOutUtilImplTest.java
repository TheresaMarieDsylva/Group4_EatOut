package com.eatOut.utilities;

import org.junit.Assert;
import org.junit.Test;

import com.eatOut.util.EatOutUtilImpl;
import com.eatOut.util.IEatOutUtil;

public class EatOutUtilImplTest {
	
IEatOutUtil eatOutUtil=new EatOutUtilImpl();
	
	@Test
    public void generate() {
		
		long firstOtp=eatOutUtil.getOneTimePassword();
		long secondOtp=eatOutUtil.getOneTimePassword();
        Assert.assertNotEquals(firstOtp, secondOtp);
    }

	
}

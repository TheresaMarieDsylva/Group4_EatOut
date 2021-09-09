package com.eatOut.util;

public class EatOutUtilImpl implements IEatOutUtil{

	@Override
	public long getOneTimePassword() {
		OneTimePassword oneTimePwd=new OneTimePassword();
		return oneTimePwd.generate();
	}

}

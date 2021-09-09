package com.eatOut.login;

public interface IUser {
    User authenticate(IUserDAO authDAO, String loginId, String password, UserType userType) throws Exception;
}


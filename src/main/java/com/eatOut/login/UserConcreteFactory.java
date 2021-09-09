package com.eatOut.login;

public class UserConcreteFactory extends UserAbstractFactory {
    @Override
    public IUser getUser() {
        return new User();
    }

    @Override
    public IUserDAO getUserDAO() {
        return new UserDAO();
    }
}

package com.eatOut.login;

public class UserConcreteFactoryTest extends UserAbstractFactoryTest {
    @Override
    public IUser getUser() {
        return new User();
    }

    @Override
    public IUserDAO getUserDAO() {
        return new UserMockDAO();
    }
}

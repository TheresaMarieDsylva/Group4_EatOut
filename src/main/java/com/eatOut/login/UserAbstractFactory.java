package com.eatOut.login;

public abstract class UserAbstractFactory {
    public abstract IUser getUser();
    public abstract IUserDAO getUserDAO();
}

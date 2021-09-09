package com.eatOut.profile;

public abstract class ProfileAbstractFactory {

    public abstract IProfile getProfile();

    public abstract IProfileDAO getProfileDao();
}

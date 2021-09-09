package com.eatOut.profile;

public class ProfileConcreteFactory extends ProfileAbstractFactory{
    @Override
    public IProfile getProfile() { return new Profile(); }

    @Override
    public IProfileDAO getProfileDao() { return new ProfileDAO(); }
}

package com.eatOut.profile;

import static org.mockito.Mockito.mock;

public class ProfileConcreteFactoryTest extends ProfileAbstractFactory{
    @Override
    public IProfile getProfile() { return new Profile(); }

    @Override
    public IProfileDAO getProfileDao() { return mock(ProfileDAO.class); }
}

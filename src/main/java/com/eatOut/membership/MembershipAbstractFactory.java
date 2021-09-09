package com.eatOut.membership;

public abstract class MembershipAbstractFactory {
    public abstract IMembership getMembership();

    public abstract IMembershipDAO getMembershipDao();
}

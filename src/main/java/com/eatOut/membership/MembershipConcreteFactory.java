package com.eatOut.membership;

public class MembershipConcreteFactory extends MembershipAbstractFactory {

    @Override
    public IMembership getMembership() {
        return new Membership();
    }

    @Override
    public IMembershipDAO getMembershipDao() {
        return new MembershipDAO();
    }
}

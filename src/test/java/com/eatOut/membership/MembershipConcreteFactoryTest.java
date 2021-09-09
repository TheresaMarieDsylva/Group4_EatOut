package com.eatOut.membership;

public class MembershipConcreteFactoryTest extends MembershipAbstractFactoryTest {
    @Override
    public IMembership getMembership() {
        return new Membership();
    }

    @Override
    public IMembershipDAO getMembershipDao() {
        return new MembershipMockDAO();
    }
}

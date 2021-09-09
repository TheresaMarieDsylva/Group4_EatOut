package com.eatOut.membership;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MembershipTest {

    MembershipAbstractFactoryTest membershipAbstractFactory;
    IMembership membership;
    IMembershipDAO membershipDao;

    @Before()
    public void init() {
        membershipAbstractFactory = new MembershipConcreteFactoryTest();
        membershipAbstractFactory.getMembership();
        membership = membershipAbstractFactory.getMembership();
        membershipDao = membershipAbstractFactory.getMembershipDao();
    }

    @Test
    public void addMembership_newCard_Test() throws Exception{
        int result = membership.addMembership(membershipDao,"Pro", 8,5);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void addMembership_emptyCard_Test() throws Exception{
        int result = membership.addMembership(membershipDao,"", 0,-2);
        Assert.assertTrue(result <= 0);
    }

    @Test
    public void disableMembership_valid_Test() throws Exception{
        int result = membership.disableMembership(membershipDao, new String[]{"Pro"});
        Assert.assertTrue(result > 0);
    }

    @Test
    public void disableMembership_invalid_Test() throws Exception{
        int result = membership.disableMembership(membershipDao, new String[]{"Pro super"});
        Assert.assertTrue(result < 0);
    }
}

package com.eatOut.testsuiteclass;


import com.eatOut.registration.AuthenticateRegistrationTest;
import com.eatOut.booktable.BookTableTest;
import com.eatOut.calendar.CalendarDateCalculatorTest;
import com.eatOut.cart.CartTest;
import com.eatOut.customercoupon.CustomerCouponTest;
import com.eatOut.coupon.CouponGeneratorTest;
import com.eatOut.customerhomepage.CustomerMembershipTest;
import com.eatOut.profile.ProfileTest;
import com.eatOut.deal.DealOfferTest;
import com.eatOut.deal.DealValidationTest;
import com.eatOut.login.UserTest;
import com.eatOut.membership.MembershipTest;
import com.eatOut.resetpassword.ResetPasswordUserAuthenticationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AuthenticateRegistrationTest.class,
         AuthenticateRegistrationTest.class,
        ResetPasswordUserAuthenticationTest.class, CustomerMembershipTest.class, BookTableTest.class, ProfileTest.class,
        CalendarDateCalculatorTest.class, CustomerCouponTest.class, CouponGeneratorTest.class,
        DealOfferTest.class, DealValidationTest.class, UserTest.class, CartTest.class,
        MembershipTest.class})

public class TestSuite {

}

package com.eatOut.resetpassword;


import org.junit.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResetPasswordUserAuthenticationTest {
    static ResetPasswordUserAuthentication pa=null;
    static ResetPasswordUserAuthentication.ResetResponseMessage rrm=null;
    static ResetPasswordChangeDAO service=null;
    @BeforeClass
    public static void setUp() {
        pa = new ResetPasswordUserAuthentication();
        service = mock(ResetPasswordChangeDAO.class);
    }

    @Test
    public void checkPasswordMatchTest(){
        String password = "hello";
        String rpassword = "hello";
        String result = pa.checkPasswordMatch(password,rpassword);
        Assert.assertEquals(rrm.OTP_HAS_BEEN_SENT_TO_YOUR_EMAIL.name(),result);
        Assert.assertNotEquals(rrm.PASSWORD_DIDNT_MATCH.name(),result);
    }
    @Test
    public void checkPasswordMismatchTest(){
        String password = "hello";
        String rpassword = "test";
        String result = pa.checkPasswordMatch(password,rpassword);
        Assert.assertEquals(rrm.PASSWORD_DIDNT_MATCH.name(),result);
        Assert.assertNotEquals(rrm.OTP_HAS_BEEN_SENT_TO_YOUR_EMAIL.name(),result);
    }

    @Test
    public void writePasswordToDBTest(){

        when(service.writeNewPassword("ajmal","123")).thenReturn(true);
        Assert.assertEquals(true,service.writeNewPassword("ajmal","123"));
    }

    @AfterClass
    public static void tearDown() {
        pa = null;
        rrm = null;
        service = null;
    }
}

package com.eatOut.resetpassword;

public class VerifyOTP {
    public enum OTPFlag{
        OTP_ENTERED_IS_WRONG,
        PASSWORD_CHANGED_SUCCESSFULLY
    }
    public String authenticateOTP(String otp){
        updatePassword();
        return OTPFlag.OTP_ENTERED_IS_WRONG.name();
    }
    public String updatePassword(){
        return OTPFlag.PASSWORD_CHANGED_SUCCESSFULLY.name();
    }

}

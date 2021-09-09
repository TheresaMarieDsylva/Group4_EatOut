package com.eatOut.resetpassword;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.eatOut.resetpassword.VerifyOTP.OTPFlag.OTP_ENTERED_IS_WRONG;

@Controller
public class OTPPasswordResetController {
    VerifyOTP votp = new VerifyOTP();


    @RequestMapping(value="/otp_pass_reset", method = {RequestMethod.POST, RequestMethod.GET})
    public String otp_received(HttpServletRequest request, HttpServletResponse response, Model model) throws InterruptedException {
        String otp = request.getParameter("otp");
        if(request.getParameter("form2Submit")==null || otp.isEmpty()){
            return "reset_password";
        }
        else{
            String displayMessage = votp.authenticateOTP(otp);
            if(displayMessage.equals(OTP_ENTERED_IS_WRONG.name())){
                model.addAttribute("displayOTPMessage","Wrong OTP!");
            }
            else{
                model.addAttribute("displayOTPMessage","Password Changed Successfully, redirecting to login in 5seconds or click below");
                return "login";

            }
        }
        return "otp_pass_reset";
    }

}

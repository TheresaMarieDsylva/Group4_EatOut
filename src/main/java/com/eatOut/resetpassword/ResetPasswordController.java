package com.eatOut.resetpassword;

import com.eatOut.mail.IEmailNotification;
import com.eatOut.mail.SMTPMailNotification;
import com.eatOut.util.EatOutUtilImpl;
import com.eatOut.util.IEatOutUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.eatOut.resetpassword.ResetPasswordUserAuthentication.ResetResponseMessage.PASSWORD_DIDNT_MATCH;

@Controller
public class ResetPasswordController {
    @RequestMapping(value="/reset_password", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewPasswordResetPage( HttpServletRequest request,  Model model) {

        String email = request.getParameter("email");
        String emails[] = new String[10];
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        IResetPasswordUserAuthentication pa = new ResetPasswordUserAuthentication();
        if(request.getParameter("form1Submit")==null) {
            return "reset_password";
        }
        else{
            String displayMessage = pa.checkPasswordMatch(password, confirmPassword);
            if(displayMessage.equals(PASSWORD_DIDNT_MATCH.name())){
                model.addAttribute("displayMessage","Password didn't match, try again!");
            }
            else {
                IEatOutUtil iEatOutUtil = new EatOutUtilImpl();
                long otp = iEatOutUtil.getOneTimePassword();
                HttpSession session = request.getSession();
                session.setAttribute("resetotp",otp);
                session.setAttribute("resetemail",email);
                session.setAttribute("otpexpire",200);
                IEmailNotification iEmailNotification = new SMTPMailNotification();
                emails[0] = email;
                iEmailNotification.sendEmail(emails,"resetOTP", "Hello, this is your reset OTP\t"+ otp);
                return "otp_pass_reset";
            }
        }
        return "reset_password";
    }

}

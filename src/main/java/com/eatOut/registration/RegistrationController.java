package com.eatOut.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import static com.eatOut.registration.AuthenticateRegistration.RegisterResponse.*;

@Controller
public class RegistrationController {

    @RequestMapping(value = "/customerRegistration", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewCustomerRegisterPage(HttpServletRequest request, Model model) throws Exception{
        String displayPage = null;
        IAuthenticateRegistration iAuthenticateRegistration = new AuthenticateRegistration();
        String firstName = request.getParameter("customer_first_name");
        String lastName = request.getParameter("customer_last_name");
        String phoneNumber = request.getParameter("customer_phone_number");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String email = request.getParameter("customer_email");
        String password = request.getParameter("customer_password");
        String confirmPassword = request.getParameter("customer_repeat_password");

        if (request.getParameter("customer-register-form") == null) {
            displayPage= "customerRegistration";
        } else {

            AuthenticateRegistration.RegisterResponse registerResponse = iAuthenticateRegistration.authenticateCustomerRegistration(firstName, lastName, phoneNumber, city, country, email, password, confirmPassword);

            if (registerResponse.equals(PASSWORD_DIDNT_MATCH)) {
                model.addAttribute("customerRegisterMessage", "Password didn't match, try again!");
                displayPage= "customerRegistration";
            } else if (registerResponse.equals(USER_ALREADY_EXISTS)) {
                model.addAttribute("customerRegisterMessage", "User already exists..");
                displayPage= "customerRegistration";
            } else {
                model.addAttribute("customerRegisterMessage", "Account successfully created. You'll be redirected to login page in 5(s)");
                displayPage= "login";
            }
        }
        return displayPage;
    }

    @RequestMapping(value="/restaurantRegistration", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewRestaurantRegisterPage(HttpServletRequest request, Model model) throws Exception {
        String displayPage=null;
        IAuthenticateRegistration iAuthenticateRegistration = new AuthenticateRegistration();
        String restaurantName = request.getParameter("restaurant_name");
        String phoneNumber = request.getParameter("restaurant_phone_number");
        String address = request.getParameter("restaurant_address");
        String city = request.getParameter("restaurant_city");
        String country = request.getParameter("restaurant_country");
        String email = request.getParameter("restaurant_email");
        String password = request.getParameter("restaurant_password");
        String confirmPassword = request.getParameter("restaurant_repeat_password");

        if(request.getParameter("restaurant-register-form")==null) {
            displayPage= "restaurantRegistration";
        }
        else{
            AuthenticateRegistration.RegisterResponse registerResponse= null;
                registerResponse = iAuthenticateRegistration.authenticateRestaurantRegistration(restaurantName,phoneNumber,address,city,country,email,password,confirmPassword);
            if (registerResponse.equals(PASSWORD_DIDNT_MATCH)) {
                model.addAttribute("restaurantRegisterMessage", "Password didn't match, try again!");
                displayPage= "restaurantRegistration";
            } else if (registerResponse.equals(USER_ALREADY_EXISTS)) {
                model.addAttribute("restaurantRegisterMessage", "User already exists. Try another user.");
                displayPage= "restaurantRegistration";
            } else {
                model.addAttribute("restaurantRegisterMessage", "Account successfully created. You'll be redirected to login page in 5(s)");
                displayPage= "login";
            }
        }
        return displayPage;
    }

    @RequestMapping(value = "/admin_register", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewAdminRegisterPage(HttpServletRequest request, Model model) {
        String displayPage = null;
        IAuthenticateRegistration iAuthenticateAuthenticateRegistration = new AuthenticateRegistration();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String adminType = request.getParameter("adminType");
        String phoneNum = request.getParameter("phoneNum");
        String city = request.getParameter("city");
        String country = request.getParameter("country");

        if (request.getParameter("adminRegisterForm") == null) {
            displayPage = "admin_register";
        } else {
            email = email.toLowerCase();
            AuthenticateRegistration.RegisterResponse registerResponse = iAuthenticateAuthenticateRegistration.authenticateAdminRegistration(email, password, confirmPassword, adminType, phoneNum, city, country);
            if (registerResponse.equals(PASSWORD_DIDNT_MATCH)) {
                model.addAttribute("adminRegisterMessage", "Password didn't match, try again!");
                displayPage = "admin_register";
            } else if (registerResponse.equals(USER_ALREADY_EXISTS)) {
                model.addAttribute("adminRegisterMessage", "User already exists..");
                displayPage = "admin_register";
            } else {
                model.addAttribute("adminRegisterMessage", "Account successfully created.");
                displayPage = "home";
            }
        }
        return displayPage;
    }
}


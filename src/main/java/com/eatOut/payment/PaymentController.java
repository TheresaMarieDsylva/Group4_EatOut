package com.eatOut.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class PaymentController {

    @RequestMapping(value = "/payment", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewPasswordResetPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Payment payment = new Payment();
        String displayPage = null;


        if(request.getParameter("payButton")==null){
            displayPage="payment";
        }
        else{

            Boolean response = payment.checkIfHasEnoughMoneyAndPay((String) session.getAttribute("customerId"),"100","takeout",(String) session.getAttribute("restaurantId"));
            if(response){
                model.addAttribute("payMessage","Payment Successful");
            }
            else{
                model.addAttribute("payMessage","Payment Not Successful, not enough wallet balance");
            }
        }

        return displayPage;
    }
}



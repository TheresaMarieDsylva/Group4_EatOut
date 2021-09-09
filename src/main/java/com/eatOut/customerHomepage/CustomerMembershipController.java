package com.eatOut.customerHomepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerMembershipController {
    ICustomerMembership customerMembership = new CustomerMembership();;
    @RequestMapping(value = "/choose_membership", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewChooseMembership(HttpServletRequest request, Model model)  {
    HttpSession session = request.getSession();
    String displayPage = null;
    String membershipName=null;
    Boolean value = false, result = false;
    int cusID = (int) session.getAttribute("resId");
    List<CustomerMembership> membershipDetails = customerMembership.displayMembership();
    model.addAttribute("cusMembershipDetails", membershipDetails);

    if(request.getParameter("addMembershipForm")==null ){
            displayPage = "choose_membership";
        }
    else{
        membershipName = (request.getParameter("membershipName")).toLowerCase();
        for(CustomerMembership temp : membershipDetails){
            if(membershipName.matches(temp.getMembershipName().toLowerCase())){
                value = customerMembership.addCustomerMembership(cusID,membershipName);
                if(value){
                    model.addAttribute("membershipResponse", "Membership Successfully added!");
                }
                else{
                    model.addAttribute("membershipResponse", "You already have this membership, choose a different one!");
                }
                result = true;
            }
        }
    }
    if(result.equals(false)){
        model.addAttribute("membershipResponse", "Enter the right membership");
    }
    displayPage = "choose_membership";
    return displayPage;
    }
}

package com.eatOut.membership;

import com.eatOut.applicationconfiguration.ApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MembershipController {
    public final String MEMBERSHIP_VIEW = "membership";
    public final String MEMBERSHIP_ADD_VIEW = "membership-add";

    MembershipAbstractFactory membershipAbstractFactory = new MembershipConcreteFactory();
    IMembership membership = membershipAbstractFactory.getMembership();
    IMembershipDAO membershipDao = membershipAbstractFactory.getMembershipDao();

    @RequestMapping(value="/membership", method= RequestMethod.GET)
    public ModelAndView viewMembership() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(MEMBERSHIP_VIEW);
        List<Membership> membershipItems = membership.loadMembership(membershipDao);
        modelAndView.addObject("membershipItems", membershipItems);
        return modelAndView;
    }

    @RequestMapping(value="/membership/membership-add", method= RequestMethod.GET)
    public String createMembership() {
        return MEMBERSHIP_ADD_VIEW;
    }

    @RequestMapping(value="/membership/submit", method= RequestMethod.POST)
    public @ResponseBody int membershipSubmit(String name, String dining, String takeaway) throws Exception {
        return membership.addMembership(membershipDao, name, Integer.parseInt(dining), Integer.parseInt(takeaway));
    }

    @RequestMapping(value="/membership/delete", method= RequestMethod.POST)
    public @ResponseBody int deleteMembership(String items) throws Exception {
        String[] membershipNames = items.split(ApplicationConfiguration.COMMA_SEPARATOR);
        return membership.disableMembership(membershipDao, membershipNames);
    }
}

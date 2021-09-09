package com.eatOut.customerHomepage;

import java.util.List;

public class CustomerMembership implements  ICustomerMembership{
    CustomerMembershipDAO customerMembershipDAO = new CustomerMembershipDAO();
    String diningPercent, membershipName,takeawayPercent;

    @Override
    public List<CustomerMembership> displayMembership() {
        List<CustomerMembership> displayCustomerMembership = customerMembershipDAO.displayMembershipDetails();
        return displayCustomerMembership;
    }

    public void setDiningPercent(String diningPercent) {
        this.diningPercent = diningPercent;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public void setTakeawayPercent(String takeawayPercent) {
        this.takeawayPercent = takeawayPercent;
    }

    public String getDiningPercent() {
        return diningPercent;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public String getTakeawayPercent() {
        return takeawayPercent;
    }

    @Override
    public Boolean addCustomerMembership(int tempCusID, String membershipName){
        Boolean value = false;
        value = customerMembershipDAO.addCustomerToMembership(tempCusID,membershipName);
        if(value){
            value =true;
        }
        return value;
    }


}

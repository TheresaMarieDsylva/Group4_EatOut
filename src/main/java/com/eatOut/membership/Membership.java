package com.eatOut.membership;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Membership implements IMembership {
    private String membershipName;
    private LocalDateTime membershipCreatedDate;
    private LocalDateTime membershipExpiryDate;
    private int dining;
    private int takeaway;
    private String membershipStatus;

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public LocalDateTime getMembershipCreatedDate() {
        return membershipCreatedDate;
    }

    public void setMembershipCreatedDate(LocalDateTime membershipCreatedDate) {
        this.membershipCreatedDate = membershipCreatedDate;
    }

    public LocalDateTime getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(LocalDateTime membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public int getDining() {
        return dining;
    }

    public void setDining(int dining) {
        this.dining = dining;
    }

    public int getTakeaway() {
        return takeaway;
    }

    public void setTakeaway(int takeaway) {
        this.takeaway = takeaway;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public List<Membership> loadMembership(IMembershipDAO membershipDAO) throws Exception {
        List<Membership> membershipRegisterList = new ArrayList<>();
        List<Map<String, Object>> memberships = membershipDAO.loadMembership();
        memberships.forEach(membershipObj -> {
            Membership membership = new Membership();
            membership.setMembershipName(membershipObj.get("membership_name").toString());
            membership.setDining((Integer) membershipObj.get("dining_percent"));
            membership.setTakeaway((Integer) membershipObj.get("takeaway_percent"));
            membership.setMembershipStatus(membershipObj.get("membership_status").toString());
            membership.setMembershipCreatedDate(LocalDateTime.parse(membershipObj.get("created_at").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            if (null != membershipObj.get("valid_through")) {
                membership.setMembershipExpiryDate(LocalDateTime.parse(membershipObj.get("valid_through").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            }
            membershipRegisterList.add(membership);
        });
        return membershipRegisterList;
    }

    @Override
    public int addMembership(IMembershipDAO membershipDAO, String membershipName, int dining, int takeaway) throws Exception {
        return membershipDAO.addMembershipCard(membershipName, dining, takeaway);
    }

    @Override
    public int disableMembership(IMembershipDAO membershipDAO, String[] membershipNames) throws Exception {
        return membershipDAO.disableMembershipCard(membershipNames);
    }
}

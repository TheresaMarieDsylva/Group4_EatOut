package com.eatOut.mail;

import org.junit.Assert;
import org.junit.Test;

public class IEmailNotificationTest {

	
	@Test
    public void sendEmailTest() {
       IEmailNotification objIEmailNotification= new SMTPMailNotificationMock();
       String recipients[]= {"rob@dal.ca"};
       boolean flag=objIEmailNotification.sendEmail(recipients, "eatOut", "test");
       Assert.assertTrue(flag);
    }
	
}

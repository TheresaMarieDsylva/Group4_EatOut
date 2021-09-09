package com.eatOut.mail;

public interface IEmailNotification {
 
	boolean sendEmail(String[] recipients,String msgSubject, String messageBody);
}

package com.eatOut.mail;

public class SMTPMailNotificationMock implements IEmailNotification{

	@Override
	public boolean sendEmail(String[] recipientsTo, String msgSubject, String messageBody) {
		return true;
	}

}

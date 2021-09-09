package com.eatOut.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SMTPMailNotification implements IEmailNotification{
	

	private static Logger log = LogManager.getLogger(SMTPMailNotification.class);
	
	private  String userName="";
	private  String password="";
	
	public SMTPMailNotification() {
		userName=System.getenv("SMTP_USER");
		password=System.getenv("SMTP_USER_PWD");
	}
	
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password);
		}
	}
	
	@Override
	public boolean sendEmail(String[] recipients,String msgSubject, String messageBody)  {
		
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", System.getenv("SMTP_HOST_NAME"));
			properties.put("mail.smtp.port", System.getenv("SMTP_PORT"));
			properties.put("mail.smtp.auth", System.getenv("SMTP_AUTH"));
			properties.put("mail.smtp.starttls.enable", System.getenv("SMTP_TLS"));
			properties.put("mail.smtp.ssl.enable", System.getenv("SMTP_SSL"));
			properties.put("mail.smtp.ssl.trust", System.getenv("SSL_TRUST"));
			
			Authenticator authenticator = new SMTPAuthenticator();
			Session smtpMailSession = Session.getInstance(properties,authenticator);
			smtpMailSession.setDebug(false);
			Message message = new MimeMessage(smtpMailSession);
			InternetAddress addressFrom = new InternetAddress(userName);
			message.setFrom(addressFrom);
			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
					addressTo[i] = new InternetAddress(recipients[i]);
			}
			message.setRecipients(Message.RecipientType.BCC, addressTo); 				
			message.setSubject(msgSubject);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(messageBody, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport transport = smtpMailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", userName, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return true;
			
		} catch (Exception e) {
			log.error("Error while sending email through SMTP Service");
			return false;
		}
	}
	
}

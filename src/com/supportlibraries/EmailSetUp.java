
package com.supportlibraries;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSetUp  {

	public static void generateMail( String filePath, boolean executionStatus) throws Exception {

		String smtpHost     =  LoadProperty.getPropertyInstance().getProperty("SMTP_HOST");
		String smtpPort     =  LoadProperty.getPropertyInstance().getProperty("SMTP_PORT");
		String recipeintId  =  LoadProperty.getPropertyInstance().getProperty("TO_MAILID");
		String ccId         =  LoadProperty.getPropertyInstance().getProperty("CC_MAILID");
		int count           =  0;
		String[] to = recipeintId.split(";");
		String[] cc = ccId.split(";");
		String from = System.getProperty("user.name")+"@mcfadyen.com";
		String host = smtpHost;
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "false");
		properties.put("mail.smtp.starttls.enabled", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", smtpPort);
		String subject;
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		if(executionStatus)
		subject="PPALE-Automation report:";
		else 
			subject="Action Required: PPALE-Automation report:";	
		
		try {
			InternetAddress[] recipientAddress = new InternetAddress[to.length];
			for(String address:to) {
				recipientAddress[count]=new InternetAddress(address.trim());
				count++;
			}
			count=0;
						
			InternetAddress[] ccRecipientAddress = new InternetAddress[cc.length];
			for(String address:cc) {
				ccRecipientAddress[count]=new InternetAddress(address.trim());
				count++;
			}
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, recipientAddress);
			message.setRecipients(Message.RecipientType.CC, ccRecipientAddress);
			message.setSubject(subject);

			BodyPart messageBodyPart = new MimeBodyPart();
			if (executionStatus)
				messageBodyPart.setContent("<p>"
						+ "*******This is an auto generated mail*******\n </p>\n\n\n Stakeholders,\n\n\n<br></br>An automated test suite has been executed.</br>Please find the report attached along with the email.\n\n\n\n<br></br>Regards,</br>McFadyen QA Automation Team</br></br><p>P.S: This is a work in progress and in a state of continuous improvement. You will find updates in subsequent test reports time-to-time.</p>",
						"text/html");
			else
				messageBodyPart.setContent("<p>"
						+ "*******This is an auto generated mail*******\n </p>\n\n\n Stakeholders,\n\n\n<br></br>An automated test suite has been executed.</br>Please find the report attached along with the email.\n\n\n\n<br></br>Regards,</br>McFadyen QA Automation Team</br></br><p>P.S: This is a work in progress and in a state of continuous improvement. You will find updates in subsequent test reports time-to-time.</p>",
						"text/html");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ExecutionReport.html");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
	
	
}

/* *** Read emails from inbox  - need to fix this

public class CheckingMails {

	public static void main(String[] args) throws MessagingException, IOException {
		String protocol="imaps";
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		                 
		    props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    props.setProperty("mail.imaps.socketFactory.fallback", "false");
		    props.setProperty("mail.imaps.port", "993");
		    props.setProperty("mail.imaps.socketFactory.port", "993");
		                 
		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore(protocol);
		store.connect("imap-mail.outlook.com", "jvijayaratnam@mcfadyen.com", "Mf123$");
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);

	   }
}

** */ 



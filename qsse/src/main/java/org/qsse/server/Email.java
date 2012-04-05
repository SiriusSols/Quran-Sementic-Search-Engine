package org.qsse.server;

import javax.mail.*;
import java.util.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.inject.Singleton;

@Singleton
public class Email {

	private String from = "noreply@email.com";
	private String subject = "QSSE:I've shared ${sampleName} ";
	private String body = "Sir, \n \n" + "You've been invited to access a Sample at ${sampleURL} \n"
			+ "Click to open: \n ${sampleLink} \n" + "Best regards, \n" + "The StatCloud9 Team";

	public void sendEmail(String toEmail, String sampleName, String sampleURL) throws Exception {

		subject = subject.replace("${sampleName}", sampleName);
		body = body.replace("${sampleURL}", sampleURL).replace("${sampleLink}", getSampleLink(sampleURL, sampleName));

		System.out.println("body - "+ body);
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(from, from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		msg.setSubject(subject);
		msg.setText(body);
		Transport.send(msg);
	}

	private String getSampleLink(String URL, String name) {
		return new StringBuilder().append("<a href=\"").append(URL).append("\" target=\"_blank\">").append(name)
				.append("</a>").toString();
	}
}
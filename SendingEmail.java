package net.pack.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendingEmail {
	ReadingEmail re =new ReadingEmail();
	String from = "kurtulusbeylan@gmail.com";
	String pass = "kurtuluS.3522";
	String to=re.fromEmail; 
	String host = "smtp.gmail.com";
	
	public void send() {
		try {
			//to=new InternetAddress();
						
			System.out.println("MAIL TOO:  "+to);
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.socketFactory.port", 465);
		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    props.put("mail.smtp.socketFactory.fallback", "false");
		    
		    GMailAuthenticator auth = new GMailAuthenticator("kurtulusbeylan@gmail.com", "kurtuluS.3522");
					
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
					 
		    message.addRecipients(Message.RecipientType.TO, to);
		
			message.setSubject("Proje gruplar�!!!");
			message.setText("Mesaj�n�z al�nm��t�r.\n Projenizde ba�ar�lar dilerim !!!");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
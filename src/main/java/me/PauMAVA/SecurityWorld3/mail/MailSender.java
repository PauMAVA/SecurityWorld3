package me.PauMAVA.SecurityWorld3.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private final String from = "sqlatsc3@gmail.com";
    private final String password = "4aaYgM734yvA48a";

    private final Properties properties = new Properties();

    private final Session session;

    public MailSender() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    public boolean sendPhase1Mail(String address) {
        try {
            InternetAddress[] internetAddress = InternetAddress.parse(address);
            return sendMail(internetAddress, "VXNlcm5hbWUgZm9yZ290dGVuIHJlcXVlc3Qu==", "V2VsY29tZSB0byBvdXIgdXNlciBkYXRhYmFzZSEKClBsZWFzZSByZXNwb25kIHdpdGggeW91ciB1c2VyIGlkIHRvIHJldHJpZXZlIHlvdXIgY3VycmVudCB1c2VybmFtZSEKClJlc3BvbnNlIHRpbWUgaXMgYWJvdXQgMzAgc2Vjb25kcy4=");
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendMail(Address[] to, String subject, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(content);
            message.addRecipients(Message.RecipientType.TO, to);
            Transport.send(message);
            return true;
        } catch (AddressException e) {
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}

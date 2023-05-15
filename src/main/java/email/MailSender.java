package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    public static void sendEmail(String toAddress, String subject, String body) throws MessagingException {

        String username = "vjl@narola.email";
        String password = "lZ4#yO7(bD0sJ9";

        Properties prop = EmailConfiguration.getInstance().getProperties();
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(body);
        try {
            Transport.send(message);
            System.out.println("Verification email sent.");
        } catch (MessagingException e) {
            System.out.println("Invalid email id.");
        }
    }
}

package com.narola.onlinefooddeliverysystemV1.email;

import com.narola.onlinefooddeliverysystemV1.exception.MailException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class MailSender {

    public void sendEmail(String toAddress, String subject, String body) throws MailException {
        try {
            Session session = EmailConfiguration.getInstance().buildSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfiguration.getInstance().getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MailException("Exception while sending mail:", e);
        }
    }

    public void sendEmail(List<String> toMailIds, String subject, String body) throws MessagingException {
        Session session = EmailConfiguration.getInstance().buildSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EmailConfiguration.getInstance().getUsername()));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
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

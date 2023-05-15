package email;

import java.util.Properties;

// EmailConnection, EmailProperties
public class EmailConfiguration {

    private static EmailConfiguration emailConfig = null;
    private Properties prop = null;


    private EmailConfiguration() {

    }

    public static EmailConfiguration getInstance() {
        if (emailConfig == null) {
            emailConfig = new EmailConfiguration();
        }
        return emailConfig;
    }

    public Properties getProperties() {
        if (prop == null) {
            prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.ionos.com");
            prop.put("mail.smtp.port", "587");

        }
        return prop;
    }
}

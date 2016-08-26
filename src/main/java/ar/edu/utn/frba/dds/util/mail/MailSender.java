package ar.edu.utn.frba.dds.util.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class MailSender {

    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public void sendMail(String subject, String body, boolean esHtml) {
        try {
            mailSender.setJavaMailProperties(PropertiesFactory.getMailProperties());
            Properties appProperties = PropertiesFactory.getAppProperties();

            mailSender.setUsername(appProperties.getProperty("user.mail.sender"));
            mailSender.setPassword(appProperties.getProperty("pass.mail.sender"));
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(appProperties.getProperty("user.mail.sender"));
            helper.setText(body, esHtml);
            helper.setSubject(subject);
            helper.addTo(appProperties.getProperty("admin.mail"));
            mailSender.send(message);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

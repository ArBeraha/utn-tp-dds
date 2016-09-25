package ar.edu.utn.frba.dds.util.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class MailSender {

    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Async
    public void sendMail(String to, String subject, String body, boolean esHtml) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
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
                    helper.addTo(to);
                    mailSender.send(message);
                    System.out.println("E-Mail enviado con éxito");
                } catch (MessagingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        
        t.start();

    }

}

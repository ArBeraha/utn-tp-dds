package ar.edu.utn.frba.dds.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory {

    private static Properties properties;
    private static Properties mailProperties;

    public static synchronized Properties getAppProperties() {
        if (properties == null) {
            try (final InputStream stream = PropertiesFactory.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                properties = new Properties();
                properties.load(stream);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
        return properties;
    }

    public static synchronized Properties getMailProperties() {
        if (mailProperties == null) {
            try (final InputStream stream = PropertiesFactory.class.getClassLoader()
                    .getResourceAsStream("mail.properties")) {
                mailProperties = new Properties();
                mailProperties.load(stream);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
        return mailProperties;
    }
}

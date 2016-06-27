package ar.edu.utn.frba.dds.util;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFactory {

    private static Properties properties;

    public static synchronized Properties getProperties() {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(new FileInputStream("./src/main/resources/application.properties"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return properties;
    }
}

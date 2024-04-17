package co.edu.javeriana.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {
    public static Properties readConfigFile() {
        Properties props = new Properties();
        try (InputStream input = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("connection.properties")) {
            if (input == null) {
                throw new FileNotFoundException("connection.properties file not found in classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load the properties file: " + e.getMessage(), e);
        }
        return props;
    }
}

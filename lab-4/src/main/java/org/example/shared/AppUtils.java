package org.example.shared;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class AppUtils {

    static final String SERVER_CONFIG_FILE = "server.properties";
    static final String KEY_PORT = "server.port";
    static final String KEY_DELAY = "server.dalayms";

    static Integer getIntegerFromProperties(String key) {

        try (InputStream input = AppUtils.class.getResourceAsStream("/" + SERVER_CONFIG_FILE)) {

            if (input == null) return null;

            Properties properties = new Properties();
            properties.load(input);

            String value = properties.getProperty(key);
            if (value == null) return null;

            return Integer.parseInt(value);

        } catch (IOException ex) {
            return null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Integer getServerPortNumberFromProperties() {

        Integer port = getIntegerFromProperties(KEY_PORT);
        if (port == null || port < 1024 || port > 49151) return null;
        return port;
    }

    public static Integer getServerDalayFromProperties() {

        Integer delayMs = getIntegerFromProperties(KEY_DELAY);
        if (delayMs == null || delayMs < 0 || delayMs > 5000) return null;
        return delayMs;
    }
}

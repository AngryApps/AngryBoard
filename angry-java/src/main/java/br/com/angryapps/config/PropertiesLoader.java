package br.com.angryapps.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final String SECRETS_PROPERTIES_FILE = "secrets.properties";

    public static void loadConfiguration() {
        // Load application properties
        loadPropertiesFile(APPLICATION_PROPERTIES_FILE);

        // Load secrets properties (will override application properties if same keys exist)
        loadPropertiesFile(SECRETS_PROPERTIES_FILE);

        loadFromEnvironment();
    }

    private static void loadPropertiesFile(String filename) {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (input != null) {
                logger.info("Loading configuration from {} file in classpath", filename);
                Properties properties = new Properties();
                properties.load(input);

                // Set each property as a system property
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    System.setProperty(key, value);
                }
            } else {
                logger.info("{} file not found in classpath", filename);
            }
        } catch (IOException e) {
            logger.error("Failed to load {} file", filename, e);
        }
    }

    private static void loadFromEnvironment() {
        // Load from environment variables
        System.getenv().forEach((key, value) -> {
            // Transform environment variable names to match property format
            String propertyKey = key.toLowerCase().replace('_', '.');
            System.setProperty(propertyKey, value);

            // Also set the original env var name as a property for compatibility
            System.setProperty(key, value);
        });
    }
}



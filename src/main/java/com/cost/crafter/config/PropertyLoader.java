package com.cost.crafter.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class PropertyLoader {

    private static PropertyLoader propertyLoader = null;
    private Properties properties = null;

    private PropertyLoader() {}

    private void load() {
        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            // load a properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public static PropertyLoader getInstance() throws SQLException {
        if (propertyLoader == null) {
            propertyLoader = new PropertyLoader();
            propertyLoader.load();
        }
        return propertyLoader;
    }
}

package com.Cabs.BDD.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getBrowserName() {
        return properties.getProperty("browser");
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait"));
    }
}

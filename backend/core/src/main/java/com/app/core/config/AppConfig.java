package com.app.core.config;

import java.io.FileInputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties props = new Properties();
    
    static {
        try {
            props.load(new FileInputStream("C:/config/application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Config file not found!", e);
        }
    }
    
    public static String getDbUrl() {
        return props.getProperty("db.url");
    }
    
    public static String getDbUser() {
        return props.getProperty("db.username");
    }
    
    public static String getDbPassword() {
        return props.getProperty("db.password");
    }
    
    public static int getTokenInitialMinutes() {
        return Integer.parseInt(props.getProperty("token.initial.minutes"));
    }
    
    public static int getTokenExtensionMinutes() {
        return Integer.parseInt(props.getProperty("token.extension.minutes"));
    }
}

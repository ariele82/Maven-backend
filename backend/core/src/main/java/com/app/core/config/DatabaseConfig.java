package com.app.core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static Connection getConnection() throws SQLException {
        try {
            // Forza il caricamento del driver PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver non trovato!", e);
        }

        return DriverManager.getConnection(
            AppConfig.getDbUrl(),
            AppConfig.getDbUser(),
            AppConfig.getDbPassword()
        );
    }
}

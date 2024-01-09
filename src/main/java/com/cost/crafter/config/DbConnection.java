package com.cost.crafter.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static DbConnection instance = null;
    private Connection conn = null;

    private DbConnection() {}

    private void init() throws SQLException {
        Properties properties = PropertyLoader.getInstance().getProperties();

        final String url = properties.getProperty("db.url");
        final String username = properties.getProperty("db.username");
        final String password = properties.getProperty("db.password");

        conn = DriverManager.getConnection(url, username, password);

        System.out.println("Connected to database");
    }

    public Connection getConnection() {
        return conn;
    }

    public static DbConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DbConnection();
            instance.init();
        }
        return instance;
    }
}

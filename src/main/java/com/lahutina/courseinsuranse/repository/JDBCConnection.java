package com.lahutina.courseinsuranse.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbc-coursework";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "1111";
    private static Connection instance;

    private JDBCConnection() {
    }

    public static Connection getConnection() throws SQLException { // #3
        if (instance == null) {
            instance = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        return instance;
    }

    public static void closeConnection() throws SQLException {
        instance.close();
        instance = null;
    }
}
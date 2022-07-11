package com.accenture.assignmentweek.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    public Connection getConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "secret");

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/stocks",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}

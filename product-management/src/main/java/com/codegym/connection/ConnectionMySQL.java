package com.codegym.connection;

import com.codegym.connection.IConnectionMySQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionMySQL implements IConnectionMySQL {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/productmanagement?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Xuka2018";
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

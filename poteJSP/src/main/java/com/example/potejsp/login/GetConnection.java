package com.example.potejsp.login;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/votedb?characterEncoding=UTF-8";
        String username = "root";
        String password = "1234";
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

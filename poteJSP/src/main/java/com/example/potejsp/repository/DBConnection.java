package com.example.potejsp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String server = "jdbc:mysql://localhost:3306/votedb?characterEncoding=UTF-8"; // MySQL 서버 주소
    private static final String user_name = "root"; //  MySQL 서버 아이디
    private static final String password = "1234"; // MySQL 서버 비밀번호


    public static Connection getConnection() {
        // 1.드라이버 로딩
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(server, user_name, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException();
        }
    }

}

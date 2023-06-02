package com.example.potejsp.login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static void getUserFromResultSet(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt("users_id"));
        user.setEmail(rs.getString("email"));
        user.setNickname(rs.getString("nickname"));
        user.setAddress(rs.getString("address"));
        user.setNaverId(rs.getString("naver_id"));
        user.setAge(rs.getInt("age"));
    }

    public static User userInsert(User user) {
        Connection conn = GetConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.ADD_USERS, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getNaverId());
            pstmt.setInt(5, user.getAge());
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> userSelectAll() {
        List<User> userList = new ArrayList<>();
        Connection conn = GetConnection.getConnection();

        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(UserQuerry.SELECT_ALL);
            while(rs.next()) {
                User user = new User();
                getUserFromResultSet(rs, user);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static User userSelectByEmail(String email) {
        User user = null;
        Connection conn = GetConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_BY_EMAIL)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                user = new User();
                getUserFromResultSet(rs, user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User userSelectByIdAndEmail(int id, String email) {
        User user = null;
        Connection conn = GetConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_BY_ID_AND_EMAIL)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                user = new User();
                getUserFromResultSet(rs, user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User userSelectByEmailAndNaverId(String email, String naverId) {
        User user = null;
        Connection conn = GetConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_BY_EMAIL_AND_NAVERID)) {
            pstmt.setString(1, email);
            pstmt.setString(2, naverId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                user = new User();
                getUserFromResultSet(rs, user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

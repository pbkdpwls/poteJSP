package com.example.potejsp.login;

import com.example.potejsp.repository.DBConnection;

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
        Connection conn = DBConnection.getConnection();
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

    public static User userUpdate(User user, String beforeNickName) {
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.UPDATE_USERS)) {
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getAddress());
            pstmt.setInt(3, user.getAge());
            pstmt.setInt(4, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            user = null;
            System.out.println(e.getMessage());
        }
        if (user != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.UPDATE_BOARDS)) {
                pstmt.setString(1, user.getNickname());
                pstmt.setString(2, beforeNickName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                user = null;
                System.out.println(e.getMessage());
            }
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
        Connection conn = DBConnection.getConnection();

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
        Connection conn = DBConnection.getConnection();

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
        Connection conn = DBConnection.getConnection();

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

    public static Integer selectMakeCountByUserNickname(String nickname) {
        Integer result = null;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_MAKE_COUNT_BY_USER_NICKNAME)) {
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                result = rs.getInt("make_count");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Integer selectVoteCountByUserId(int userId) {
        Integer result = null;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_VOTE_COUNT_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                result = rs.getInt("vote_count");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String selectItemNameByUserId(int userId) {
        String result = null;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_ITEM_NAME_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User userSelectByEmailAndNaverId(String email, String naverId) {
        User user = null;
        Connection conn = DBConnection.getConnection();

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

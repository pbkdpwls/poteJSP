package com.example.potejsp.login;

import com.example.potejsp.repository.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String[] REGION = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
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

    public static int selectMakeCountByUserNickname(String nickname) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_MAKE_COUNT_BY_USER_NICKNAME)) {
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
    public static int selectVoteCountByUserId(int userId) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_VOTE_COUNT_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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

    public static int countMyChoiceResultByUserId(int userId) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.COUNT_MY_CHOICE_RESULT_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("my_choice_count");
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
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_ITEM_NAME_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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

    public static String selectChoiceNameByUserId(int userId) {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_CHOICE_NAME_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("vote_result");
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

    public static int selectRegionCount(int regionNum) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_REGION_COUNT)) {
            pstmt.setString(1, REGION[regionNum]);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("region_count");
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

    public static int countAllUser() {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.COUNT_ALL_USER)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("users_count");
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

    public static int countAllBoard() {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.COUNT_ALL_BOARD)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("board_count");
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

    public static int countAllVoter() {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.COUNT_ALL_VOTER)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("voter_count");
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

    public static String selectBestVoteResult() {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_BEST_VOTE_RESULT)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("vote_result");
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

    public static String selectBestChoiceResult() {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(UserQuerry.SELECT_BEST_CHOICE_RESULT)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
}

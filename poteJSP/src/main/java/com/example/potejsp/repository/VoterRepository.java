package com.example.potejsp.repository;

import java.sql.*;
import java.util.HashMap;

public class VoterRepository {
    public static final String INSERT_VOTER = "INSERT INTO voter (users_id, item_id) VALUES (?, ?)";
    public static final String FIND_USER_ID = "SELECT users_id FROM voter WHERE item_id = ?";
    public static final String FIND_USER_ID_IN_BOARD = "SELECT users_id, voter.item_id, board_id " +
            "FROM voter " +
            "JOIN item ON item.item_id = voter.item_id " +
            "WHERE board_id = ?";
    public static final String DELETE_VOTER = "DELETE\n" +
            "FROM voter\n" +
            "WHERE voter.users_id = ?\n" +
            "  AND voter.item_id = ?";
    public static final String UPDATE_ITEM_ID = "UPDATE voter SET item_id = ? WHERE users_id = ? AND item_id = ?";

    // 투표하기
    public int vote(int userId, int itemId) {
        Connection connection = DBConnection.getConnection();

        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_VOTER, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return result; // 쿼리 실행 실패 시 음수
    }

    // 중복투표 방지
    public int validateVoter(int mode, int userId, int itemId, int boardId) {
        Connection connection = DBConnection.getConnection();

        int result = 1;

        try {
            PreparedStatement statement = null;
            ResultSet rs = null;

            if (mode == 0) { // 동일 아이템에 중복투표 불가
                statement = connection.prepareStatement(FIND_USER_ID);
                statement.setInt(1, itemId);
            } else if (mode == 1) { // 동일 게시글에 중복투표 불가
                statement = connection.prepareStatement(FIND_USER_ID_IN_BOARD);
                statement.setInt(1, boardId);
            }

            if (statement != null) {
                rs = statement.executeQuery();
                while (rs.next()) {
                    if(userId == rs.getInt("users_id")){
                        result = -1;
                        break;
                    }
                }
            }

            if (rs != null) {
                rs.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 투표 취소
    public int undoVote(int userId, int itemId){
        Connection connection = DBConnection.getConnection();

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_VOTER)){
            statement.setInt(1,userId);
            statement.setInt(2,itemId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 투표 아이템 변경
    public int updateItemId(int userId, int currentItemId, int newItemId) {
        Connection connection = DBConnection.getConnection();

        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_ITEM_ID)) {
            pstmt.setInt(1, newItemId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, currentItemId);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}


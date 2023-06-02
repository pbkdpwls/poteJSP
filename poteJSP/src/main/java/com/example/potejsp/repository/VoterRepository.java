package com.example.potejsp.repository;

import java.sql.*;

public class VoterRepository {
    public static final String INSERT_VOTER = "INSERT INTO voter (user_id, board_id) VALUES (?, ?)";

    public int vote(int userId, int itemId) {
        Connection connection = DBConnection.getConnection();

        int result =0;
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_VOTER, Statement.RETURN_GENERATED_KEYS);){
            pstmt.setInt(1, userId);
            pstmt.setInt(2, itemId);
            result = pstmt.executeUpdate();

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
}


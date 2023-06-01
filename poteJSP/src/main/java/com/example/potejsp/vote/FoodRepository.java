package com.example.potejsp.vote;

import java.sql.*;

public class FoodRepository {

    //targetVote의 id를 food에 외래키로 지정하여 저장
    public Food saveFood(int voteId, Food food) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO food (name, decision, vote_id) VALUES (?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // INSERT 쿼리에 파라미터 설정
        pstmt.setString(1, food.getName());
        pstmt.setBoolean(2, food.isDecision());
        pstmt.setInt(3, voteId);

        pstmt.executeUpdate();

        // 생성된 ID 값 가져오기
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            food.setFoodId(generatedId);
        }

        System.out.println("저장 완료");

        connection.close();

        return food;

    }
}

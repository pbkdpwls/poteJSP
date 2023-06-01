package com.example.potejsp.repository;

import com.example.potejsp.domain.Item;

import java.sql.*;

public class ItemRepository {

    //targetVote의 id를 food에 외래키로 지정하여 저장
    public Item saveFood(int boardId, Item item) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO item (name, board_id) VALUES (?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // INSERT 쿼리에 파라미터 설정
        pstmt.setString(1, item.getName());
        pstmt.setInt(2, boardId);

        pstmt.executeUpdate();

        // 생성된 ID 값 가져오기
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            item.setItemId(generatedId);
        }

        System.out.println("저장 완료");

        connection.close();

        return item;
    }
}

package com.example.potejsp.vote;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VoteRepository {

    //저장
    public Vote saveVote(Vote vote) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO vote (title, start_date, end_date, nickname, address) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // INSERT 쿼리에 파라미터 설정
        pstmt.setString(1, vote.getTitle());
        pstmt.setString(2, vote.getStartDate().toString());
        pstmt.setString(3, vote.getEndDate().toString());
        pstmt.setString(4, vote.getNickname());
        pstmt.setString(5, vote.getAddress());

        pstmt.executeUpdate();

        // 생성된 ID 값 가져오기
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            vote.setVoteId(generatedId);
        }

        System.out.println("저장 완료");

        connection.close();

        return vote;
    }


    //전체조회 (페이징)
    public List<Vote> findAll(int pageNumber) throws SQLException {
        int pageSize = 5; // 페이지당 결과 수

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM vote ORDER BY start_date DESC LIMIT ?, ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, (pageNumber - 1) * pageSize);
        pstmt.setInt(2, pageSize);

        ResultSet rs = pstmt.executeQuery();

        List<Vote> votes = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("vote_id");
            String title = rs.getString("title");
            LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
            LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
            String nickname = rs.getString("nickname");
            String address = rs.getString("address");

            Vote vote = new Vote(id, title, startDate, endDate, nickname, address);
            votes.add(vote);
        }

        connection.close();

        return votes;
    }




}

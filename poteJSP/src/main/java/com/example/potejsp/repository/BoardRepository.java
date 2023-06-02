package com.example.potejsp.repository;


import com.example.potejsp.domain.Board;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {

    //저장
    public Board saveBoard(Board board) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO board (title, start_date, end_date, nickname, address,isProgressed) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // INSERT 쿼리에 파라미터 설정
        pstmt.setString(1, board.getTitle());
        pstmt.setString(2, LocalDateTime.now().toString());
        pstmt.setString(3, board.getEndDate().toString());
        pstmt.setString(4, board.getNickname());
        pstmt.setString(5, board.getAddress());
        pstmt.setBoolean(6, board.getIsProgressed());

        pstmt.executeUpdate();

        // 생성된 ID 값 가져오기
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            board.setBoardId(generatedId);
        }

        System.out.println("저장 완료");

        connection.close();

        return board;
    }

    // 전체 게시물 수 반환 메서드
    public int getTotalBoardCount() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT COUNT(*) AS count FROM board";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int totalCount = 0;
        if (resultSet.next()) {
            totalCount = resultSet.getInt("count");
        }

        connection.close();

        return totalCount;
    }

    //전체조회 (페이징)
    public List<Board> findAll(int pageNumber) throws SQLException {
        int pageSize = 5; // 페이지당 결과 수

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM board ORDER BY start_date DESC LIMIT ?, ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, (pageNumber - 1) * pageSize);
        pstmt.setInt(2, pageSize);

        ResultSet rs = pstmt.executeQuery();

        List<Board> boards = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("board_id");
            String title = rs.getString("title");
            LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
            LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
            String nickname = rs.getString("nickname");
            String address = rs.getString("address");
            boolean isProgressed = rs.getBoolean("isProgressed");

            Board board = new Board(id, title, startDate, endDate, nickname, address, isProgressed);
            boards.add(board);
        }

        connection.close();

        return boards;
    }


    //board의 title, nickname 으로 검색
    public List<Board> searchByKeyword(String keyword) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM board WHERE title LIKE ? OR nickname LIKE ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keyword + "%");
        pstmt.setString(2, "%" + keyword + "%");

        ResultSet rs = pstmt.executeQuery();

        List<Board> boards = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("board_id");
            String title = rs.getString("title");
            LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
            LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
            String nickname = rs.getString("nickname");
            String address = rs.getString("address");
            boolean isProgressed = rs.getBoolean("isProgressed");

            Board board = new Board(id, title, startDate, endDate, nickname, address, isProgressed);
            boards.add(board);
        }

        connection.close();

        return boards;
    }



//    private static final String SELECT_ALL_QUERY = "SELECT * FROM board WHERE ";
//    private static final String TITLE_CONDITION = "title LIKE ?";
//    private static final String NICKNAME_CONDITION = "nickname LIKE ?";
//    private static final String PROGRESS_LIST_QUERY = "end_date > NOW()";
//
//    public static List<Board> searchByTitle(String target) throws SQLException {
//        return executeSearchQuery(SELECT_ALL_QUERY + TITLE_CONDITION, "%" + target + "%");
//    }
//
//    public static List<Board> searchByNickname(String nickname) throws SQLException {
//        return executeSearchQuery(SELECT_ALL_QUERY + NICKNAME_CONDITION, "%" + nickname + "%");
//    }
//
//    public static List<Board> progressList() throws SQLException {
//        return executeSearchQuery(SELECT_ALL_QUERY + PROGRESS_LIST_QUERY);
//    }
//
//    private static List<Board> executeSearchQuery(String query, Object... params) throws SQLException {
//        Connection connection = DBConnection.getConnection();
//        PreparedStatement statement = connection.prepareStatement(query);
//
//        for (int i = 0; i < params.length; i++) {
//            statement.setObject(i + 1, params[i]);
//        }
//
//        ResultSet rs = statement.executeQuery();
//
//        List<Board> list = new ArrayList<>();
//        while (rs.next()) {
//            Board board = new Board();
//            board.setBoardId(rs.getInt("board_id"));
//            board.setAddress(rs.getString("address"));
//            board.setTitle(rs.getString("title"));
//            board.setNickname(rs.getString("nickname"));
//            board.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
//            board.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
//            board.setIsProgressed(rs.getBoolean("isProgressed"));
//            list.add(board);
//        }
//
//        rs.close();
//        statement.close();
//        connection.close();
//        return list;
//    }

}

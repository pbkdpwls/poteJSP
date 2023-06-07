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

        String sql = "INSERT INTO board (title, start_date, users_count, nickname, address,isProgressed) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // INSERT 쿼리에 파라미터 설정
        pstmt.setString(1, board.getTitle());
        pstmt.setString(2, board.getStartDate().toString());
        pstmt.setInt(3, board.getUsersCount());
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


    //board의 투표수 조회하기
    public int selectVoterCount(int boardId) throws SQLException {
        String sql = "select count(v.voter_id) from voter v " +
                "join item i on i.item_id = v.item_id " +
                "join board b on b.board_id = i.board_id " +
                "where b.board_id = ?";

        int count = 0;

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, boardId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.close();

        return count;
    }


    //users_count 조회
    public int selectUsersCount(int boardId) throws SQLException {
        String sql = "select users_count from board where board_id = ?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        int count = 0;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, boardId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.close();

        return count;
    }


    //IsProgressed 조회
    public int selectIsProgressed(int boardId) throws SQLException {
        String sql = "select isProgressed from board where board_id = ?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        int isProgressed = 0;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, boardId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                isProgressed = rs.getInt("isProgressed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.close();

        return isProgressed;

    }

    //board의 isProgressed 업데이트
    public void updateIsProgressed(int boardId) {
        String sql = "update board set isProgressed = 0 where board_id = ?;";

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, boardId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("isProgressed 업데이트 성공");
            } else {
                System.out.println("isProgressed 업데이트 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    //투표 결과(vote_result) 입력
    public void updateVoteResult(int boardId, String voteResult) {
        String sql = "UPDATE board SET vote_result = ? WHERE board_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnection.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, voteResult);
            pstmt.setInt(2, boardId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
            int usersCount = rs.getInt("users_count");
            String nickname = rs.getString("nickname");
            String address = rs.getString("address");
            boolean isProgressed = rs.getBoolean("isProgressed");

            Board board = new Board(id, title, startDate, usersCount, nickname, address, isProgressed);
            boards.add(board);
        }

        connection.close();

        return boards;
    }


    //board의 title, nickname 으로 검색
    public List<Board> searchByKeyword(String keyword, int pageNumber) throws SQLException {
        int pageSize = 5; // 페이지당 결과 수

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM board WHERE title LIKE ? OR nickname LIKE ? ORDER BY start_date DESC LIMIT ?, ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keyword + "%");
        pstmt.setString(2, "%" + keyword + "%");
        pstmt.setInt(3, (pageNumber - 1) * pageSize);
        pstmt.setInt(4, pageSize);

        ResultSet rs = pstmt.executeQuery();

        List<Board> boards = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("board_id");
            String title = rs.getString("title");
            LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
            int usersCount = rs.getInt("users_count");
            String nickname = rs.getString("nickname");
            String address = rs.getString("address");
            boolean isProgressed = rs.getBoolean("isProgressed");

            Board board = new Board(id, title, startDate, usersCount, nickname, address, isProgressed);
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

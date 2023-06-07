package com.example.potejsp.repository;


import com.example.potejsp.domain.Board;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {

    //저장
    public Board saveBoard(Board board) {
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.INSERT_BOARD, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getStartDate().toString());
            pstmt.setInt(3, board.getUsersCount());
            pstmt.setString(4, board.getNickname());
            pstmt.setString(5, board.getAddress());
            pstmt.setBoolean(6, board.getIsProgressed());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                board.setBoardId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }


    //users_count 조회
    public int selectUsersCount(int boardId) {
        Connection connection = DBConnection.getConnection();
        int count = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.SELECT_USER_COUNT_BY_BOARD_ID)) {
            pstmt.setInt(1, boardId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static String selectBestVoteResult() {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(BoardQuery.SELECT_BEST_VOTE_RESULT)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("vote_result");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //IsProgressed 조회
    public int selectIsProgressed(int boardId) {
        Connection connection = DBConnection.getConnection();
        int isProgressed = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.SELECT_PROGRESSED_BY_BOARD_ID)) {
            pstmt.setInt(1, boardId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                isProgressed = rs.getInt("isProgressed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isProgressed;
    }

    public static int countMyChoiceResultByUserId(int userId) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(BoardQuery.COUNT_MY_CHOICE_RESULT_BY_USER_ID)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("my_choice_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int selectMakeCountByUserNickname(String nickname) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(BoardQuery.SELECT_MAKE_COUNT_BY_USER_NICKNAME)) {
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("make_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        try (PreparedStatement pstmt = conn.prepareStatement(BoardQuery.COUNT_BOARD)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //board의 isProgressed 업데이트
    public void updateIsProgressed(int boardId) {
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.UPDATE_PROGRESSED_BY_BOARD_ID)) {
            pstmt.setInt(1, boardId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("isProgressed 업데이트 성공");
            } else {
                System.out.println("isProgressed 업데이트 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //투표 결과(vote_result) 입력
    public void updateVoteResult(int boardId, String voteResult) {
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.UPDATE_BOARD_RESULT)) {
            pstmt.setString(1, voteResult);
            pstmt.setInt(2, boardId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 작성글의 주소와 투표자의 주소 검증
    public int validateAddress(int boardId, int userId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(BoardQuery.GET_BOARD_ADDR);
             PreparedStatement statement2 = connection.prepareStatement(UserQuery.GET_USER_ADDR)) {
            statement1.setInt(1, boardId);
            ResultSet rs1 = statement1.executeQuery();
            String boardAddress = "";
            while (rs1.next()) {
                boardAddress = rs1.getString("address");
            }

            statement2.setInt(1, userId);
            ResultSet rs2 = statement2.executeQuery();
            String userAddress = "";
            while (rs2.next()) {
                userAddress = rs2.getString("address");
            }
            // 주소가 일치하지 않은면 -1을 return
            return userAddress.equals(boardAddress) ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 전체 게시물 수 반환 메서드
    public int getTotalBoardCount() {
        Connection connection = DBConnection.getConnection();
        int totalCount = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(BoardQuery.COUNT_BOARD);
            if (resultSet.next()) {
                totalCount = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    //전체조회 (페이징)
    public List<Board> findAll(int pageNumber) throws SQLException {
        int pageSize = 5; // 페이지당 결과 수
        List<Board> boards = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.SELECT_ALL_BOARD)) {
            pstmt.setInt(1, (pageNumber - 1) * pageSize);
            pstmt.setInt(2, pageSize);
            ResultSet rs = pstmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }


    //board의 title, nickname 으로 검색
    public List<Board> searchByKeyword(String keyword, int pageNumber) {
        int pageSize = 5; // 페이지당 결과 수
        Connection connection = DBConnection.getConnection();
        List<Board> boards = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(BoardQuery.SELECT_BOARD_BY_SEARCH)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setInt(3, (pageNumber - 1) * pageSize);
            pstmt.setInt(4, pageSize);

            ResultSet rs = pstmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
}

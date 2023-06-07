package com.example.potejsp.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoterRepository {
    // 투표하기
    public int vote(int userId, int itemId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다. - AutoClosable interface
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(VoterQuery.INSERT_VOTER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 중복투표 방지
    public int validateVoter(int mode, int userId, int itemId, int boardId) {
        int result = 1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(VoterQuery.FIND_USER_ID);
             PreparedStatement statement2 = connection.prepareStatement(VoterQuery.FIND_USER_ID_IN_BOARD)) {
            statement1.setInt(1, itemId);
            ResultSet rs = statement1.executeQuery();
            while (rs.next()) {
                if (userId == rs.getInt("users_id")) {
                    result = -1;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    // 투표 취소하기
    public int undoVote(int userId, int itemId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(VoterQuery.DELETE_VOTER)) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            return statement.executeUpdate(); // 성공시 양수 return
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 실패시 0 return
    }

    // 투표 다시하기
    public int reVote(int userId, int newItemId, int boardId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다. - AutoClosable interface
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(VoterQuery.GET_CUR_ITEM_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, boardId);
            ResultSet rs = statement.executeQuery();
            int currentItemId = 0;
            while (rs.next()) {
                currentItemId = rs.getInt("voter.item_id");
                System.out.println("cur_item_id: " + currentItemId);
            }

            if (currentItemId == newItemId) {
                return -1; // 변경할 아이템과 기존아이템이 동일한 경우 -1 return
            }
            try (PreparedStatement pstmt = connection.prepareStatement(VoterQuery.UPDATE_ITEM_ID)) {
                pstmt.setInt(1, newItemId);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, currentItemId);
                pstmt.setInt(4, boardId);
                return pstmt.executeUpdate(); // 성공시 양수 return
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 아무 동작이 실행되지 않으면 0 return
    }

    // 투표자 리스트 구하기
    public HashMap<Integer, List<String>> getVoterListMap(int boardId) {
        // 동일 아이템id에 해당하는 유저의 닉네임을 리스트로 받는다.
        HashMap<Integer, List<String>> map = new HashMap<>();
        // try문이 끝나면 알아서 connection과 statement가 닫힌다. - AutoClosable interface
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(VoterQuery.GET_VOTER_LIST)) {
            statement.setInt(1, boardId);
            ResultSet rs = statement.executeQuery();
            int newItemId = 0;
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String nickname = rs.getString("nickname");
                // 아이템 아이디가 바뀔 때 마다 새로운 리스트를 만들어 아이템아이디(key) 당 다른 리스트(value)가 들어간다.
                if (newItemId != itemId) {
                    newItemId = itemId;
                    List<String> voterList = new ArrayList<>();
                    voterList.add(nickname);
                    map.put(itemId, voterList);
                } else {
                    List<String> voterList = map.get(itemId);
                    voterList.add(nickname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public HashMap<String, Integer> getVoteCount(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        HashMap<String, Integer> foodNameAndVoteCount = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(VoterQuery.SELECT_VOTE_COUNT_QUERY)) {
            statement.setInt(1, boardId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                foodNameAndVoteCount.put(rs.getString("name"), rs.getInt("투표수"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodNameAndVoteCount;
    }

    public static int selectVoteCountByUserId(int userId) {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(VoterQuery.SELECT_VOTE_COUNT_BY_USER_ID)) {
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

    public static String selectItemNameByUserId(int userId) {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(VoterQuery.SELECT_ITEM_NAME_BY_USER_ID)) {
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

        try (PreparedStatement pstmt = conn.prepareStatement(VoterQuery.SELECT_CHOICE_NAME_BY_USER_ID)) {
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

    public static int countAllVoter() {
        int result = 0;
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(VoterQuery.COUNT_ALL_VOTER)) {
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

    //board의 투표수 조회하기
    public int selectVoterCount(int boardId) throws SQLException {
        int count = 0;
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(VoterQuery.SELECT_COUNT_VOTER_BY_BOARD_ID)) {
            pstmt.setInt(1, boardId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}

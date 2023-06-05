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
             PreparedStatement statement = connection.prepareStatement(Query.INSERT_VOTER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 중복투표 방지
    public int validateVoter(int mode, int userId, int itemId, int boardId) {
        Connection connection = DBConnection.getConnection();
        int result = 1;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs = null;
        try {
            statement1 = connection.prepareStatement(Query.FIND_USER_ID);
            statement1.setInt(1, itemId);
            rs = statement1.executeQuery();
            while (rs.next()) {
                if (userId == rs.getInt("users_id")) {
                    result = -1;
                    break;
                }
            }
            statement2 = connection.prepareStatement(Query.FIND_USER_ID_IN_BOARD);
            statement2.setInt(1, boardId);
            rs = statement2.executeQuery();

            while (rs.next()) {
                if (userId == rs.getInt("users_id")) {
                    result = -1;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 닫지 않은 statement, rs가 있는지 확인하고 닫기
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // 작성글의 주소와 투표자의 주소 검증
    public int validateAddress(int boardId, int userId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(Query.GET_BOARD_ADDR);
             PreparedStatement statement2 = connection.prepareStatement(Query.GET_USER_ADDR)) {
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

    // 투표 취소하기
    public int undoVote(int userId, int itemId) {
        // try문이 끝나면 알아서 connection과 statement가 닫힌다.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.DELETE_VOTER)) {
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
             PreparedStatement statement = connection.prepareStatement(Query.GET_CUR_ITEM_ID)) {
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

            try (PreparedStatement pstmt = connection.prepareStatement(Query.UPDATE_ITEM_ID)) {
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
             PreparedStatement statement = connection.prepareStatement(Query.GET_VOTER_LIST)) {
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
}

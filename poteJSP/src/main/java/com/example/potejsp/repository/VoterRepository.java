package com.example.potejsp.repository;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoterRepository {
    public static final String INSERT_VOTER = "INSERT INTO voter (users_id, item_id) VALUES (?, ?)";
    public static final String FIND_USER_ID = "SELECT users_id, voter_id FROM voter WHERE item_id = ?";
    public static final String FIND_USER_ID_IN_BOARD = "SELECT users_id, voter.item_id, board_id " +
            "FROM voter " +
            "JOIN item ON item.item_id = voter.item_id " +
            "WHERE board_id = ?";
    public static final String DELETE_VOTER = "DELETE\n" +
            "FROM voter\n" +
            "WHERE voter.users_id = ?\n" +
            "  AND voter.item_id = ?";
    public static final String UPDATE_ITEM_ID = "UPDATE voter \n" +
            "JOIN item i ON voter.item_id = i.item_id\n" +
            "SET voter.item_id = ?\n" +
            "WHERE users_id = ?\n" +
            "  AND voter.item_id = ?\n" +
            "  AND i.board_id = ?;\n";

    public static final String GET_CUR_ITEM_ID = "select voter.item_id\n" +
            "from voter\n" +
            "join item i on voter.item_id = i.item_id\n" +
            "WHERE users_id = ? AND board_id =?";

    public static final String GET_VOTER_LIST = "SELECT nickname, i.item_id, i.name\n" +
            "FROM voter\n" +
            "join users u on voter.users_id = u.users_id\n" +
            "join item i on voter.item_id = i.item_id\n" +
            "where board_id = ? order by voter.item_id";

    public static final String GET_BOARD_ADDR = "SELECT board_id, address from board where board_id = ?";
    public static final String GET_USER_ADDR = "SELECT users_id, address FROM users WHERE users_id=?";


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

        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs = null;

        try {
            statement1 = connection.prepareStatement(FIND_USER_ID);
            statement1.setInt(1, itemId);
            rs = statement1.executeQuery();

            while (rs.next()) {
                if (userId == rs.getInt("users_id")) {
                    result = -1;
                    break;
                }
            }

            statement2 = connection.prepareStatement(FIND_USER_ID_IN_BOARD);
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

    // 작성자와 투표자가 같은 주소인지 검증
    public int validateAddress(int board_id, int users_id) {
        Connection connection = DBConnection.getConnection();
        int result = 0;
        String boardAddress = "";
        String userAddress = "";
        try (PreparedStatement statement = connection.prepareStatement(GET_BOARD_ADDR)) {
            statement.setInt(1, board_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                boardAddress = rs.getString("address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_ADDR)) {
            statement.setInt(1, users_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                userAddress = rs.getString("address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result = userAddress.equals(boardAddress) ? 1 : -1;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 투표 취소
    public int undoVote(int userId, int itemId) {
        Connection connection = DBConnection.getConnection();

        int result = 0;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_VOTER)) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
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

    // 투표한 아이템 변경
    public int reVote(int userId, int newItemId, int boardId) {
        Connection connection = DBConnection.getConnection();

        int result = 0;
        int currentItemId = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_CUR_ITEM_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, boardId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                currentItemId = rs.getInt("voter.item_id");
                System.out.println("cur_item_id : " + currentItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 똑같은 항목으로 투표 시 -1 return
        result = currentItemId == newItemId ? -1 : 0;

        if (result != -1) {
            try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_ITEM_ID)) {
                pstmt.setInt(1, newItemId);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, currentItemId);
                pstmt.setInt(4, boardId);
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<Integer, List<String>> getVoterListMap(int boardId) {
        HashMap<Integer, List<String>> map = new HashMap<>();
        Connection connection = DBConnection.getConnection();
        int newItemId = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_VOTER_LIST)) {
            statement.setInt(1, boardId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String nickname = rs.getString("nickname");

                if (newItemId != itemId) {
                    newItemId = itemId;
                    List<String> voterlist = new ArrayList<>();
                    voterlist.add(nickname);
                    map.put(itemId, voterlist);
                } else {
                    List<String> voterlist = map.get(itemId);
                    voterlist.add(nickname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
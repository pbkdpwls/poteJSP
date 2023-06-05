package com.example.potejsp.repository;

public class Query {
    public static final String INSERT_VOTER = "INSERT INTO voter (users_id, item_id) VALUES (?, ?)";
    public static final String FIND_USER_ID = "SELECT users_id, voter_id FROM voter WHERE item_id = ?";
    public static final String FIND_USER_ID_IN_BOARD = "SELECT users_id, voter.item_id, board_id " +
            "FROM voter " +
            "JOIN item ON item.item_id = voter.item_id " +
            "WHERE board_id = ?";
    public static final String DELETE_VOTER = "DELETE FROM voter WHERE users_id = ? AND item_id = ?";
    public static final String UPDATE_ITEM_ID = "UPDATE voter " +
            "JOIN item i ON voter.item_id = i.item_id " +
            "SET voter.item_id = ? " +
            "WHERE users_id = ? AND voter.item_id = ? AND i.board_id = ?";

    public static final String GET_CUR_ITEM_ID = "SELECT voter.item_id " +
            "FROM voter " +
            "JOIN item i ON voter.item_id = i.item_id " +
            "WHERE users_id = ? AND board_id = ?";

    public static final String GET_VOTER_LIST = "SELECT nickname, i.item_id, i.name " +
            "FROM voter " +
            "JOIN users u ON voter.users_id = u.users_id " +
            "JOIN item i ON voter.item_id = i.item_id " +
            "WHERE board_id = ? ORDER BY voter.item_id";

    public static final String GET_BOARD_ADDR = "SELECT board_id, address FROM board WHERE board_id = ?";
    public static final String GET_USER_ADDR = "SELECT users_id, address FROM users WHERE users_id = ?";

    public static final String SELECT_ITEMS_QUERY = "SELECT item_id, name, board_id FROM item WHERE board_id = ?";
    public static final String INSERT_ITEM_QUERY = "INSERT INTO item (name, board_id) VALUES (?, ?)";
    public static final String SELECT_VOTE_COUNT_QUERY = "SELECT item.item_id, item.name, count(item.item_id) as 투표수\n" +
            "FROM voter\n" +
            "JOIN item ON voter.item_id = item.item_id\n" +
            "JOIN board b ON item.board_id = b.board_id\n" +
            "JOIN users u ON voter.users_id = u.users_id\n" +
            "WHERE item.board_id = ?\n" +
            "GROUP BY item.item_id, item.name";
    public static final String SELECT_ITEM_NAMES_QUERY = "SELECT * FROM item WHERE board_id = ?";

}

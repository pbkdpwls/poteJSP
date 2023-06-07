package com.example.potejsp.repository;

public class VoterQuery {
    public static final String INSERT_VOTER = "INSERT INTO voter (users_id, item_id) VALUES (?, ?)";

    public static final String SELECT_VOTE_COUNT_BY_USER_ID = "select count(voter_id) as vote_count from voter where users_id = ? group by users_id";

    public static final String FIND_USER_ID = "SELECT users_id, voter_id FROM voter WHERE item_id = ?";

    public static final String FIND_USER_ID_IN_BOARD = "SELECT users_id, voter.item_id, board_id " +
            "FROM voter " +
            "JOIN item ON item.item_id = voter.item_id " +
            "WHERE board_id = ?";

    public static final String SELECT_COUNT_VOTER_BY_BOARD_ID = "select count(v.voter_id) from voter v " +
            "join item i on i.item_id = v.item_id " +
            "join board b on b.board_id = i.board_id " +
            "where b.board_id = ?";

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

    public static final String SELECT_VOTE_COUNT_QUERY = "SELECT item.item_id, item.name, count(item.item_id) as 투표수 " +
            "FROM voter " +
            "JOIN item ON voter.item_id = item.item_id " +
            "JOIN board b ON item.board_id = b.board_id " +
            "JOIN users u ON voter.users_id = u.users_id " +
            "WHERE item.board_id = ? " +
            "GROUP BY item.item_id, item.name";

    public static final String COUNT_ALL_VOTER = "select count(*) as voter_count from voter";

    public static final String SELECT_CHOICE_NAME_BY_USER_ID = "select bo.vote_result from voter as vt " +
            "join item it on it.item_id = vt.item_id " +
            "join board bo on bo.board_id = it.board_id " +
            "where users_id = ? and bo.vote_result is not null" +
            "group by bo.vote_result " +
            "order by count(*) desc limit 1";

    public static final String SELECT_ITEM_NAME_BY_USER_ID = "select it.name from voter as vt " +
            "join item it on it.item_id = vt.item_id " +
            "where users_id = ? " +
            "group by it.name " +
            "order by count(*) desc limit 1";
}

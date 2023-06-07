package com.example.potejsp.repository;

public class BoardQuery {
    public static final String GET_BOARD_ADDR = "SELECT board_id, address FROM board WHERE board_id = ?";
    public static final String SELECT_MAKE_COUNT_BY_USER_NICKNAME = "select count(*) as make_count from board where nickname = ?";
    public static final String UPDATE_BOARD_NICKNAME = "update board set nickname = ? where nickname = ?";
    public static final String SELECT_USER_COUNT_BY_BOARD_ID = "select users_count from board where board_id = ?";
    public static final String SELECT_PROGRESSED_BY_BOARD_ID = "select isProgressed from board where board_id = ?";
    public static final String UPDATE_PROGRESSED_BY_BOARD_ID = "update board set isProgressed = 0 where board_id = ?";
    public static final String UPDATE_BOARD_RESULT = "UPDATE board SET vote_result = ? WHERE board_id = ?";
    public static final String COUNT_BOARD = "SELECT COUNT(*) AS count FROM board";
    public static final String SELECT_ALL_BOARD = "SELECT * FROM board ORDER BY start_date DESC LIMIT ?, ?";
    public static final String SELECT_BOARD_BY_SEARCH = "SELECT * FROM board WHERE title LIKE ? OR nickname LIKE ? ORDER BY start_date DESC LIMIT ?, ?";
    public static final String INSERT_BOARD = "INSERT INTO board (title, start_date, users_count, nickname, address,isProgressed) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String COUNT_MY_CHOICE_RESULT_BY_USER_ID = "select count(*) as my_choice_count " +
            "from board bo " +
            "join item as it on bo.board_id = it.board_id " +
            "join voter as vo ON it.item_id = vo.item_id " +
            "where vo.users_id = ? and it.name = bo.vote_result";
    public static final String SELECT_BEST_VOTE_RESULT = "select vote_result from board where vote_result is not null group by vote_result order by count(board_id) desc limit 1";

}

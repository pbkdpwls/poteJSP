package com.example.potejsp.login;

public class UserQuerry {
    public static final String ADD_USERS = "insert into users(email, nickname, address, naver_id, age) " +
            "values (?, ?, ?, ?, ?)";
    public static final String SELECT_ALL = "select * from users";
    public static final String SELECT_BY_EMAIL = "select * from users where email = ?";
    public static final String SELECT_BY_ID_AND_EMAIL = "select * from users where users_id = ? and email = ?";
    public static final String SELECT_BY_EMAIL_AND_NAVERID = "select * from users where email = ? and naver_id = ?";
    public static final String UPDATE_USERS = "update users set nickname = ?, address = ?, age = ? where users_id = ?";
    public static final String UPDATE_BOARDS = "update board set nickname = ? where nickname = ?";
    public static final String SELECT_MAKE_COUNT_BY_USER_NICKNAME = "select count(*) as make_count from board where nickname = ?";
    public static final String SELECT_VOTE_COUNT_BY_USER_ID = "select count(voter_id) as vote_count from voter where users_id = ? group by users_id";
    public static final String SELECT_ITEM_NAME_BY_USER_ID = "select it.name from voter as vt " +
            "join item it on it.item_id = vt.item_id " +
            "where users_id = ? " +
            "group by it.name " +
            "order by count(*) desc limit 1";

    public static final String SELECT_CHOICE_NAME_BY_USER_ID = "select bo.vote_result from voter as vt " +
            "join item it on it.item_id = vt.item_id " +
            "join board bo on bo.board_id = it.board_id " +
            "where users_id = ? " +
            "group by bo.vote_result " +
            "order by count(*) desc limit 1";

    public static final String SELECT_REGION_COUNT = "select count(*) as region_count from users where address = ?";
    public static final String COUNT_ALL_USER = "select count(*) as users_count from users";
    public static final String COUNT_ALL_BOARD = "select count(*) as board_count from board";
    public static final String COUNT_ALL_VOTER = "select count(*) as voter_count from voter";
    public static final String SELECT_BEST_VOTE_RESULT = "select vote_result from board group by vote_result order by count(board_id) desc limit 1";
    public static final String SELECT_BEST_CHOICE_RESULT = "select name from item group by name order by count(item_id) desc limit 1";

    public static final String COUNT_MY_CHOICE_RESULT_BY_USER_ID = "select count(*) as my_choice_count " +
            "from board bo " +
            "join item as it on bo.board_id = it.board_id " +
            "join voter as vo ON it.item_id = vo.item_id " +
            "where vo.users_id = ? and it.name = bo.vote_result";
}

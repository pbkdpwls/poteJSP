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
}

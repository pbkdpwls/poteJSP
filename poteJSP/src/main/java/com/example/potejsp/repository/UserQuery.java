package com.example.potejsp.repository;

public class UserQuery {
    public static final String ADD_USERS = "insert into users(email, nickname, address, naver_id, age) " +
            "values (?, ?, ?, ?, ?)";
    public static final String SELECT_ALL = "select * from users";
    public static final String GET_USER_ADDR = "SELECT users_id, address FROM users WHERE users_id = ?";
    public static final String SELECT_BY_EMAIL = "select * from users where email = ?";
    public static final String SELECT_BY_ID_AND_EMAIL = "select * from users where users_id = ? and email = ?";
    public static final String SELECT_BY_EMAIL_AND_NAVERID = "select * from users where email = ? and naver_id = ?";
    public static final String UPDATE_USERS = "update users set nickname = ?, address = ?, age = ? where users_id = ?";
    public static final String SELECT_REGION_COUNT = "select count(*) as region_count from users where address = ?";
    public static final String COUNT_ALL_USER = "select count(*) as users_count from users";

}

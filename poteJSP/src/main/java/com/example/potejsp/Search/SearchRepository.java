package com.example.potejsp.Search;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchRepository {
    private static final String SELECT_ALL_QUERY = "SELECT * FROM vote WHERE ";
    private static final String TITLE_CONDITION = "title LIKE ?";
    private static final String NICKNAME_CONDITION = "nickname LIKE ?";
    private static final String PROGRESS_LIST_QUERY = "end_date > NOW()";

    public static List<VoteModel> searchByTitle(String target) throws SQLException {
        return executeSearchQuery(SELECT_ALL_QUERY + TITLE_CONDITION, "%" + target + "%");
    }

    public static List<VoteModel> searchByNickname(String nickname) throws SQLException {
        return executeSearchQuery(SELECT_ALL_QUERY + NICKNAME_CONDITION, "%" + nickname + "%");
    }

    public static List<VoteModel> progressList() throws SQLException {
        return executeSearchQuery(SELECT_ALL_QUERY + PROGRESS_LIST_QUERY);
    }

    private static List<VoteModel> executeSearchQuery(String query, Object... params) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        ResultSet rs = statement.executeQuery();

        List<VoteModel> list = new ArrayList<>();
        while (rs.next()) {
            VoteModel vote = new VoteModel();
            vote.setVote_id(rs.getInt("vote_id"));
            vote.setAddress(rs.getString("address"));
            vote.setTitle(rs.getString("title"));
            vote.setNickname(rs.getString("nickname"));
            vote.setStart_date(rs.getTimestamp("start_date").toLocalDateTime());
            vote.setEnd_date(rs.getTimestamp("end_date").toLocalDateTime());
            list.add(vote);
        }

        rs.close();
        statement.close();
        connection.close();
        return list;
    }
}

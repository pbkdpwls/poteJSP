package com.example.potejsp.repository;

import com.example.potejsp.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepository {
    private static final String SELECT_ITEMS_QUERY = "SELECT item_id, name, board_id FROM item WHERE board_id = ?";
    private static final String INSERT_ITEM_QUERY = "INSERT INTO item (name, board_id) VALUES (?, ?)";
    private static final String SELECT_VOTE_COUNT_QUERY = "SELECT item.item_id, item.name, count(item.item_id) as 투표수\n" +
            "FROM voter\n" +
            "JOIN item ON voter.item_id = item.item_id\n" +
            "JOIN board b ON item.board_id = b.board_id\n" +
            "JOIN users u ON voter.users_id = u.users_id\n" +
            "WHERE item.board_id = ?\n" +
            "GROUP BY item.item_id, item.name";
    private static final String SELECT_ITEM_NAMES_QUERY = "SELECT * FROM item WHERE board_id = ?";

    public List<Item> getItemList(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ITEMS_QUERY);
        statement.setInt(1, boardId);
        List<Item> itemList = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            Item item = new Item();
            item.setName(rs.getString("name"));
            item.setBoardId(rs.getInt("board_id"));
            item.setItemId(rs.getInt("item_id"));
            itemList.add(item);
        }
        rs.close();
        statement.close();
        connection.close();
        return itemList;
    }

    public Item saveFood(int boardId, Item item) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(INSERT_ITEM_QUERY, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, item.getName());
        pstmt.setInt(2, boardId);
        pstmt.executeUpdate();
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            item.setItemId(generatedId);
        }
        System.out.println("저장 완료");
        connection.close();
        return item;
    }

    public HashMap<String, Integer> getVoteCount(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_VOTE_COUNT_QUERY);
        statement.setInt(1, boardId);
        ResultSet rs = statement.executeQuery();
        HashMap<String, Integer> foodNameAndVoteCount = new HashMap<>();
        while (rs.next()) {
            foodNameAndVoteCount.put(rs.getString("name"), rs.getInt("투표수"));
        }
        rs.close();
        statement.close();
        connection.close();
        return foodNameAndVoteCount;
    }

    public HashMap<Integer, String> getItemNameInBoard(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ITEM_NAMES_QUERY);
        statement.setInt(1, boardId);
        ResultSet rs = statement.executeQuery();
        HashMap<Integer, String> map = new HashMap<>();
        while (rs.next()) {
            map.put(rs.getInt("item_id"), rs.getString("name"));
        }
        rs.close();
        statement.close();
        connection.close();
        return map;
    }
}

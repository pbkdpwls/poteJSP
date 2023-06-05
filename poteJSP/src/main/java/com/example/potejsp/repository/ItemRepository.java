package com.example.potejsp.repository;

import com.example.potejsp.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepository {
    public List<Item> getItemList(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Query.SELECT_ITEMS_QUERY);
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
        PreparedStatement pstmt = connection.prepareStatement(Query.INSERT_ITEM_QUERY, Statement.RETURN_GENERATED_KEYS);
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

    // 아이템 이름당 투표수 구하기
    public HashMap<String, Integer> getVoteCount(int boardId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Query.SELECT_VOTE_COUNT_QUERY);
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

    // 게시글의 아이템이름 가져오기
    public HashMap<Integer, String> getItemNameInBoard(int boardId){
        HashMap<Integer, String> map = new HashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.SELECT_ITEM_NAMES_QUERY)
           ){
            statement.setInt(1, boardId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("item_id"), rs.getString("name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return map;
    }
}

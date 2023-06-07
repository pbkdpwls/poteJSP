package com.example.potejsp.repository;

import com.example.potejsp.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepository {
    public List<Item> getItemList(int boardId) {
        Connection connection = DBConnection.getConnection();
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ItemQuery.SELECT_ITEMS_QUERY)) {
            statement.setInt(1, boardId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setBoardId(rs.getInt("board_id"));
                item.setItemId(rs.getInt("item_id"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public Item saveFood(int boardId, Item item) {
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(ItemQuery.INSERT_ITEM_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, boardId);
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                item.setItemId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // 게시글의 아이템이름 가져오기
    public HashMap<Integer, String> getItemNameInBoard(int boardId){
        HashMap<Integer, String> map = new HashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ItemQuery.SELECT_ITEM_BY_BOARD_ID)
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

    public static String selectBestChoiceResult() {
        String result = "";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(ItemQuery.SELECT_BEST_CHOICE_RESULT)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

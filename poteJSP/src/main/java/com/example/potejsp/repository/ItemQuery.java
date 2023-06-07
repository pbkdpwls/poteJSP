package com.example.potejsp.repository;

public class ItemQuery {
    public static final String SELECT_ITEMS_QUERY = "SELECT item_id, name, board_id FROM item WHERE board_id = ?";
    public static final String INSERT_ITEM_QUERY = "INSERT INTO item (name, board_id) VALUES (?, ?)";
    public static final String SELECT_ITEM_BY_BOARD_ID = "SELECT * FROM item WHERE board_id = ?";
    public static final String SELECT_BEST_CHOICE_RESULT = "select name from item group by name order by count(item_id) desc limit 1";

}

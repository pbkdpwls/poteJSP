package com.example.potejsp.vote;

public class Item {

    public int itemId;

    public String name;

    public int boardId;

    public Item() {
    }

    public Item(int itemId, String name, int boardId) {
        this.itemId = itemId;
        this.name = name;
        this.boardId = boardId;
    }
    public Item(String name, int boardId) {
        this.name = name;
        this.boardId = boardId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getBoardId() {
        return boardId;
    }
}

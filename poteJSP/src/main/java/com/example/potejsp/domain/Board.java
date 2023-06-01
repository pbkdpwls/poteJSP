package com.example.potejsp.domain;

import java.time.LocalDateTime;

public class Board {

    public int boardId;

    public String title;

    public LocalDateTime startDate;

    public LocalDateTime endDate;

    public String nickname;
    public String address;


    public Board() {
    }

    public Board(int boardId, String title, LocalDateTime startDate, LocalDateTime endDate, String nickname, String address) {
        this.boardId = boardId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nickname = nickname;
        this.address = address;
    }

    public Board(String title, LocalDateTime startDate, LocalDateTime endDate, String nickname, String address) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nickname = nickname;
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

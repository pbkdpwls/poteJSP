package com.example.potejsp.Search;

import java.time.LocalDateTime;

public class VoteModel {
    public int vote_id;
    public String title;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String nickname;
    public String address;

    public VoteModel() {
    }

    public VoteModel(int vote_id, String title, LocalDateTime start_date, LocalDateTime end_date, String address) {
        this.vote_id = vote_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.address = address;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVote_id() {
        return vote_id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public String getAddress() {
        return address;
    }
}

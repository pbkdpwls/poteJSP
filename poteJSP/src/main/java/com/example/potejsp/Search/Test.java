package com.example.potejsp.Search;
import java.sql.SQLException;
import java.util.*;
import com.example.potejsp.Search.SearchRepository;

public class Test {
    public static void main(String[] args) throws SQLException {
        SearchRepository searchRepository = new SearchRepository();
        List<VoteModel> list;
        list = searchRepository.searchByTitle("버거킹");
        for (VoteModel voteModel : list) {
            System.out.println(voteModel.getAddress());
        }
        list = searchRepository.searchByNickname("예진");
        for (VoteModel voteModel : list) {
            System.out.println(voteModel.getAddress());
        }
        list = searchRepository.progressList();
        for (VoteModel voteModel : list) {
            System.out.println(voteModel.getTitle());
        }
    }
}

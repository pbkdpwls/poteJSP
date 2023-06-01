package com.example.potejsp.vote;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class VoteFoodTest {

    public static void main(String[] args) throws SQLException {
        VoteRepository voteRepository = new VoteRepository();

//        Vote vote = new Vote("제목", LocalDateTime.now(), LocalDateTime.of(2023, 6,1,16,30), "이름", "주소");
//        Vote saveVote = voteRepository.saveVote(vote);
//
//        FoodRepository foodRepository = new FoodRepository();
//        Food food = new Food("네네치킨", true, saveVote.getVoteId());
//        foodRepository.saveFood(vote.getVoteId(), food);
//        System.out.println(food.getFoodId());
//        System.out.println(food.getName());
//        System.out.println(food.isDecision());
//        System.out.println(food.getVoteId());


//        for (Vote findVote : voteRepository.findAll(2)) {
//            System.out.println(findVote.getVoteId());
//            System.out.println(findVote.getTitle());
//            System.out.println(findVote.getStartDate());
//            System.out.println(findVote.getEndDate());
//            System.out.println(findVote.getAddress());
//            System.out.println("===========================");
//        }


//        List<Vote> list;
//        list = voteRepository.searchByTitle("버거킹");
//        for (Vote voteModel : list) {
//            System.out.println(voteModel.getAddress());
//        }
//        list = voteRepository.searchByNickname("예진");
//        for (Vote voteModel : list) {
//            System.out.println(voteModel.getAddress());
//        }
//        list = voteRepository.progressList();
//        for (Vote voteModel : list) {
//            System.out.println(voteModel.getTitle());
//        }


    }
}

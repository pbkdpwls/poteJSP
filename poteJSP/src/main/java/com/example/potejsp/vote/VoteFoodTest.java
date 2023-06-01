package com.example.potejsp.vote;

import java.sql.SQLException;
import java.time.LocalDateTime;

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


        for (Vote findVote : voteRepository.findAll(2)) {
            System.out.println(findVote.getVoteId());
            System.out.println(findVote.getTitle());
            System.out.println(findVote.getStartDate());
            System.out.println(findVote.getEndDate());
            System.out.println(findVote.getAddress());
            System.out.println("===========================");
        }


    }
}

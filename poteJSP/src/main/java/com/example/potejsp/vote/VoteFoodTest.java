package com.example.potejsp.vote;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class VoteFoodTest {

    public static void main(String[] args) throws SQLException {
        BoardRepository boardRepository = new BoardRepository();

        Board board = new Board("제목", LocalDateTime.now(), LocalDateTime.of(2023, 6,1,16,30), "이름", "주소");
        Board saveVote = boardRepository.saveBoard(board);

//        FoodRepository foodRepository = new FoodRepository();
//        Food food = new Food("네네치킨", true, saveVote.getBoardId());
//        foodRepository.saveFood(board.getBoardId(), food);
//        System.out.println(food.getFoodId());
//        System.out.println(food.getName());
//        System.out.println(food.isDecision());
//        System.out.println(food.getVoteId());


//        for (Vote findVote : voteRepository.findAll(1)) {
//            System.out.println(findVote.getVoteId());
//            System.out.println(findVote.getTitle());
//            System.out.println(findVote.getStartDate());
//            System.out.println(findVote.getEndDate());
//            System.out.println(findVote.getAddress());
//            System.out.println("===========================");
//        }


//        List<Board> list;
//        list = boardRepository.searchByTitle("버거킹");
//        for (Board boardModel : list) {
//            System.out.println(boardModel.getAddress());
//        }
//        list = boardRepository.searchByNickname("예진");
//        for (Board boardModel : list) {
//            System.out.println(boardModel.getAddress());
//        }
//        list = boardRepository.progressList();
//        for (Board boardModel : list) {
//            System.out.println(boardModel.getTitle());
//        }


    }
}

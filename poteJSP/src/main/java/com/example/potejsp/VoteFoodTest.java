package com.example.potejsp;


import com.example.potejsp.domain.Board;
import com.example.potejsp.repository.BoardRepository;

import java.sql.SQLException;
import java.util.List;

public class VoteFoodTest {

    public static void main(String[] args) throws SQLException {
        BoardRepository boardRepository = new BoardRepository();
//
//        Board board = new Board("제목", LocalDateTime.now(), LocalDateTime.of(2023, 6,1,16,30), "이름", "주소");
//        Board saveBoard = boardRepository.saveBoard(board);
//
//        ItemRepository itemRepository = new ItemRepository();
//        Item item = new Item("네네치킨", saveBoard.getBoardId());
//        itemRepository.saveFood(board.getBoardId(), item);
//        System.out.println(item.getItemId());
//        System.out.println(item.getName());
//        System.out.println(item.getName());
//        System.out.println(item.getBoardId());


//        for (Board findBoard : boardRepository.findAll(1)) {
//            System.out.println(findBoard.getBoardId());
//            System.out.println(findBoard.getTitle());
//            System.out.println(findBoard.getStartDate());
//            System.out.println(findBoard.getEndDate());
//            System.out.println(findBoard.getAddress());
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


//        List<Board> findBoard = boardRepository.searchByKeyword("조우현");
//        for (Board board : findBoard) {
//            System.out.println(board.getBoardId());
//            System.out.println(board.getTitle());
//            System.out.println(board.getStartDate());
//            System.out.println(board.getEndDate());
//            System.out.println(board.getNickname());
//            System.out.println(board.getAddress());
//            System.out.println(board.getIsProgressed());
//            System.out.println("================================");
//        }

                for (Board findBoard : boardRepository.findAll(2)) {
            System.out.println(findBoard.getBoardId());
            System.out.println(findBoard.getTitle());
            System.out.println(findBoard.getStartDate());
            System.out.println(findBoard.getEndDate());
            System.out.println(findBoard.getAddress());
            System.out.println("===========================");
        }


    }
}

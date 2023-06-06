<%@ page import="com.example.potejsp.domain.Board" %>
<%@ page import="com.example.potejsp.repository.BoardRepository" %>
<%@ page import="com.example.potejsp.domain.Item" %>
<%@ page import="com.example.potejsp.repository.ItemRepository" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%! User user = null; %>
<%
    request.setCharacterEncoding("UTF-8");
    String token = (String) session.getAttribute("token");
    if (token == null) {
        response.sendRedirect("index.jsp");
    }
    user = JWToken.validTokenAndGetUser(token);
    if (user == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>vote create</title>
    <meta charset="UTF-8">
</head>
<body>
<%
    if (request.getMethod().equals("POST")) {
        System.out.printf("생성 버튼 클릭");
        // 폼으로 전달된 값들을 받아옵니다.
        String title = request.getParameter("title");
        String date = request.getParameter("date");
        String usersCount = request.getParameter("usersCount");
        String location = request.getParameter("location");
        String address = request.getParameter("address");
        String[] menus = new String[5];
        for (int i = 1; i <= 5; i++) {
            menus[i - 1] = request.getParameter("menu" + i);
        }

        // Board 객체 생성 및 값 설정
        Board board = new Board(title, LocalDateTime.parse(date), Integer.parseInt(usersCount), user.getNickname(), address,true);


        try {
            // BoardRepository를 통해 Board를 저장합니다.
            BoardRepository boardRepository = new BoardRepository();
            board = boardRepository.saveBoard(board);

            // 폼으로 전달된 메뉴들을 Item 객체로 생성하고 Board에 연결하여 저장합니다.
            ItemRepository itemRepository = new ItemRepository();
            for (String menu_ : menus) {
                if (menu_ != null && !menu_.isEmpty()) {
                    Item item = new Item();
                    item.setName(menu_);
                    itemRepository.saveFood(board.getBoardId(), item);
                }
            }

            out.print("<h2>투표 생성 성공</h2>");
            response.sendRedirect("main.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h2>투표 생성 시에 오류가 발생</h2>");
        }
    }
%>
</body>
</html>

<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.repository.BoardRepository" %>
<%@ page import="com.example.potejsp.domain.Board" %>
<%@ page import="com.example.potejsp.domain.Item" %>
<%@ page import="com.example.potejsp.repository.ItemRepository"%>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%! User user = null; %>
<%
        String token = (String) session.getAttribute("token");
    if (token == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
    user = JWToken.validTokenAndGetUser(token);
    if (user == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
    <style>
        body{
            margin:0;
            padding:0;
            width:100%;
        }
        .header {
            height: 70px;
            width: 100%;
            background-color: #FFFFFF;
            box-sizing: border-box;
            padding-right: 20px;
        }

        .divider {
            height: 2px;
            width: 100%;
            background-color: #DBDBDB;
            margin-bottom: 50px;
        }

        .indexBody{
            width: 100%;
            margin: 0 auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .indexHeader{
            display: flex;
            flex-direction: row;
            margin-bottom: 10px;
        }

        .indexHeaderSearchInput{
            width: 500px;
            height: 30px;
            font-size: 15px;
            padding:10px;
            box-sizing: border-box;
            margin-right:180px;
        }

        .indexHeaderTitle{
            font-size: 30px;
            font-weight: bold;
            margin-right: 30px;
        }

        .indexHeaderFilter{
            fotn-size: 18px;
            font-weight: bold;
            color: dimgray;
        }

        .buttons {
            float: right;
            margin-top: 25px;
        }

        .buttons button {
            font-weight: bold;
            font-size: 18px;
            border: none;
            background: none;
            padding: 0;
            margin-right: 10px;
        }

        .loggedOut {
            display: none;
        }

        .loggedIn {
            display: none;
        }

        .component {
            width: 1000px;
            height: 160px;
            padding: 30px;
            box-sizing: border-box;
            background-color: #CDDBF6;
            border-radius: 10px;
            cursor: pointer;
            transition: background-color 0.5s;
            margin-bottom: 10px;
        }

        .details {
            width: 1000px;
            display: none;
            background-color: #EBF0F9;
            padding: 10px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }

        .details .item div {
            width: 800px;
            margin: 10px;
            padding: 10px;
            box-sizing: border-box;
            background-color: #FFFFFF;
            font-weight: bold;
        }

        .btn {
            background-color: #CDDBF6;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            cursor: pointer;
            margin: 10px;
            font-weight: bold;
        }
        .item div.selected {
            border: 2px solid red;
        }
    </style>
</head>
<script>
    var condition = 2; // 조건 변수, 1 또는 2로 설정

    window.onload = function() {
        if (condition === 1) {
            document.querySelector('.loggedOut').style.display = 'block';
        } else if (condition === 2) {
            document.querySelector('.loggedIn').style.display = 'block';
        }
    };

    function toggleDetails(boardId) {
        var details = document.getElementById("details"+boardId);
        details.style.display = (details.style.display === "none") ? "block" : "none";

        var component = document.querySelector(".component[data-boardId='" + boardId + "']");
        component.style.border = (details.style.display === "none") ? "none" : "2px solid red";
    }

    var selectedItemId = null; // 선택된 아이템의 ID를 저장할 변수

    function toggleItem(element) {
        var itemId = element.getAttribute("data-itemId"); // 클릭한 아이템의 ID를 가져옴

        // 선택된 아이템이 있는 경우
        if (selectedItemId !== null) {
            var selectedItem = document.querySelector(".item div[data-itemId='" + selectedItemId + "']");
            selectedItem.classList.remove("selected"); // 이전에 선택된 아이템의 스타일 제거
        }

        // 클릭한 아이템이 선택되어 있는 아이템과 같은 경우 (해제)
        if (selectedItemId === itemId) {
            selectedItemId = null; // 선택된 아이템 ID 초기화
            return;
        }

        // 클릭한 아이템에 선택된 스타일 적용
        element.classList.add("selected");
        selectedItemId = itemId; // 선택된 아이템 ID 저장
    }
</script>
<body>
<div class="header">
    <div class="buttons loggedOut">
        <button>로그인</button>
        <button>회원가입</button>
    </div>
    <div class="buttons loggedIn">
        <button onclick="location='voteGenerate.jsp'">투표생성</button>
        <button>로그아웃</button>
    </div>
</div>
<div class="divider"></div>

<div class="indexBody">
    <div class="indexHeader">
        <div class="indexHeaderTitle">투표 목록</div>
        <input type="text" name="searchInput" id="searchInput" class="indexHeaderSearchInput">
        <div class="indexHeaderFilter">
            진행중인 투표만 보기
            <label>
                <input type="checkbox" id="isVoted" name="isVoted" onclick="isVoteChecked()">
            </label>
        </div>
    </div>

    <%
        BoardRepository boardRepository = new BoardRepository();
        ItemRepository itemRepository = new ItemRepository();

        List<Board> boardList = null;
        List<Item> itemList = null;

        try {
            boardList = boardRepository.findAll(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 게시물 목록 출력
        for (Board board : boardList) {
    %>
    <div class="component" onclick="toggleDetails(<%=board.getBoardId()%>)" style="<%= board.getIsProgressed() == false ? "background-color: #F5F5F5;" : ""%>">
        <div style="font-weight: bold; font-size: 30px; margin-top:20px; color: <%= board.getIsProgressed() == false ? "darkgray" : "black" %>"><%= board.getTitle()%></div>
        <div style="font-weight: bold; font-size: 17px; margin-top:5px"><%= board.getEndDate()%> / <%= board.getAddress()%> / <%= board.getNickname()%></div>
    </div>
    <div class="details" id="details<%=board.getBoardId()%>">
        <div class="item">
            <%
                try {
                    itemList = itemRepository.getItemList(board.getBoardId());
                    for (Item item : itemList) { %>
            <div onclick="toggleItem(this)"><%= item.getName() %></div>
            <% } %>
            <%
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </div>
        <div class="btn" onclick="toggleDetails(<%=board.getBoardId()%>)">확인</div>
    </div>
    <%
        }
    %>

</div>
</body>
</html>


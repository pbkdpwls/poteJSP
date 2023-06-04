<%--
  Created by IntelliJ IDEA.
  User: Kim
  Date: 2023-06-04
  Time: 오후 5:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.potejsp.repository.VoterRepository" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.potejsp.domain.Item" %>
<%@ page import="com.example.potejsp.repository.ItemRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%! User user = null; %>
<%
  String token = (String) session.getAttribute("token");
  if (token == null) {
    response.sendRedirect("index.jsp");
    return;
  }
  user = JWToken.validTokenAndGetUser(token);
  if (user == null) {
    response.sendRedirect("index.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Vote</title>
  <style>
    .voter-list {
      margin: 20px;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .item {
      margin-bottom: 10px;
    }

    .item-id {
      font-weight: bold;
    }

    .voters {
      margin-left: 20px;
    }
  </style>
</head>
<body>
<%
  int user_id = user.getId();

  // 폼에서 값 받아오기
  int board_id = Integer.parseInt(request.getParameter("board_id"));
  VoterRepository voterRepository = new VoterRepository();
  ItemRepository itemRepository = new ItemRepository();

  // voterlist 가져오기
  HashMap<Integer, List<String>> map = voterRepository.getVoterListMap(board_id);
  HashMap<Integer, String> nameMap = itemRepository.getItemNameInBoard(board_id);
%>

<div class="voter-list">
  <% if (map.isEmpty()) { %>
  <p>투표한 인원이 없습니다.</p>
  <% } else { %>
  <% for (Map.Entry<Integer, List<String>> entry : map.entrySet()) { %>
  <div class="item">
    <span class="item-id">투표 항목: <%= nameMap.get(entry.getKey()) %></span>
    <ul class="voters">
      <% for (String voter : entry.getValue()) { %>
      <li><%= voter %></li>
      <% } %>
    </ul>
  </div>
  <% } %>
  <% } %>
</div>
</body>
</html>

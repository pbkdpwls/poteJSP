<%--
  Created by IntelliJ IDEA.
  User: Kim
  Date: 2023-06-03
  Time: 오전 8:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Test</title>
</head>
<body>
<h1>Test Page</h1>
<form action="doVote.jsp" method="post">
  user_id<input type="number" name="user_id" value=""><br/>
  item_id<input type="number" name="item_id" value=""><br/>
  board_id<input type="number" name="board_id" value=""><br/>
  <input type="submit" value="Vote">
</form>
<br/>
<form action="undoVote.jsp" method="post">
  <input type="submit" value ="삭제">
</form>
</body>
</html>

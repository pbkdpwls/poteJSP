<%--
  Created by IntelliJ IDEA.
  User: Kim
  Date: 2023-06-03
  Time: 오후 3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.potejsp.repository.VoterRepository" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.login.User" %>
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

</head>
<body>
<%
    int user_id = user.getId();

    // 폼에서 값 받아오기
    int item_id = Integer.parseInt(request.getParameter("item_id"));
    int board_id = Integer.parseInt(request.getParameter("board_id"));
    System.out.println("new_item_id: " + item_id);
    System.out.println("board_id: " + board_id);

    VoterRepository voterRepository = new VoterRepository();
    int result = 0;

    // 투표 실행
    result = voterRepository.reVote(user_id, item_id,board_id);
%>


<script>
    <%-- 투표 완료 알림창 출력 및 main.jsp로 이동 --%>
    <% if (result > 0) { %>
    alert("투표를 변경하였습니다.");
    //window.location.replace("test.jsp") // 테스트용 나중에 지우기
    window.location.replace("main.jsp"); // main으로, 히스토리가 남지 않음.(뒤로가기 해도 doVote로 안감)
    <% } else { %>
    alert("투표하지 않은 항목입니다. 다시 시도하여 주십시오");
    window.location.replace("main.jsp"); // main으로, 히스토리가 남지 않음.(뒤로가기 해도 doVote로 안감)
    <% } %>
</script>

</body>
</html>

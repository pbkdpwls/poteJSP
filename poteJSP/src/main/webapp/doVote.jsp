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
    String item_id_param = request.getParameter("item_id");
    String board_id_param = request.getParameter("board_id");
    System.out.println(item_id_param);
    if (item_id_param == null || board_id_param == null) {
%>
<script>
    alert("투표할 항목을 선택해주세요");
    window.location.replace("main.jsp");
</script>
<%
        return;
    }

    int item_id = Integer.parseInt(item_id_param);
    int board_id = Integer.parseInt(board_id_param);

    VoterRepository voterRepository = new VoterRepository();
    int result = 0;

    // 권한 검사
    int val = voterRepository.validateVoter(0, user_id, item_id, board_id);
    System.out.println(val);
    if (val > 0) {
        // 투표 실행
        result = voterRepository.vote(user_id, item_id);
    } else {
        System.out.println("중복 투표 - 권한 없음");
%>
<script>
    alert("이미 투표한 항목입니다.");
    window.location.replace("main.jsp");
</script>
<%
    }
%>

<script>
    <%-- 투표 완료 알림창 출력 및 main.jsp로 이동 --%>
    <% if (result > 0) { %>
    alert("(｡˃ ᵕ ˂ )b : 투표완료!!!");
    window.location.replace("main.jsp"); // main으로, 히스토리가 남지 않음.(뒤로가기 해도 doVote로 안감)
    <% } else { %>
    alert("서버에 접속할 수 없습니다."); // DB Insert 실패
    <% } %>
</script>

</body>
</html>

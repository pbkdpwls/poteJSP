<%@ page import="com.example.potejsp.repository.VoterRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vote</title>

</head>
<body>
<%
    // 폼에서 값 받아오기
    int user_id = Integer.parseInt(request.getParameter("user_id"));
    int item_id = Integer.parseInt(request.getParameter("item_id"));
    int board_id = Integer.parseInt(request.getParameter("board_id"));

    VoterRepository voterRepository = new VoterRepository();
    int result = 0;

// 권한 검사
    int val = voterRepository.validateVoter(0,user_id, item_id,board_id);
    System.out.println(val);
    if (val > 0) {
        // 투표 실행
        result = voterRepository.vote(user_id, item_id);
    } else {
        System.out.println("중복 투표");
%>
<script>
    alert("이미 투표를 한 항목입니다.");
    window.location.replace("test.jsp"); // 테스트용 나중에 지우기
</script>
<%
    }
%>


<script>
    <%-- 투표 완료 알림창 출력 및 main.jsp로 이동 --%>
    <% if (result > 0) { %>
    alert("★♣♡♠◆!! 투표 완료 !!★♣♡♠◆");
    window.location.replace("test.jsp") // 테스트용 나중에 지우기
    //window.location.replace("main.jsp"); // main으로, 히스토리가 남지 않음.(뒤로가기 해도 doVote로 안감)
    <% } else { %>
    alert("서버에 접속할 수 없습니다."); // DB Insert 실패
    <% } %>
</script>

</body>
</html>

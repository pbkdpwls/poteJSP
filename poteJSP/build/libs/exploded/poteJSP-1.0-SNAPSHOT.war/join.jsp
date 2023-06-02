<%@ page import="com.example.potejsp.login.APIUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    APIUser apiUser = (APIUser) session.getAttribute("apiUser");
    if (apiUser == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
%>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
    <h2>회원가입</h2>
    <form action="insert.jsp" method="post">
        <input type="text" id="email" name="email" value="<%=apiUser.getEmail()%>" readonly>
        <input type="text" id="nickname" name="nickname" placeholder="Nickname">
        <input type="text" id="address" name="address" placeholder="Address">
        <input type="password" id="naverId" name="naverId" value="<%=apiUser.getNaverId()%>" readonly>
        <input type="text" id="age" name="age" placeholder="Age">
        <button type="submit" class="insert-button">가입</button>
    </form>
</body>
</html>

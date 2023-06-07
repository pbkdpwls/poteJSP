<%@ page import="com.example.potejsp.login.UserDAO" %>
<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.domain.User" %>
<%@ page import="com.example.potejsp.repository.UserRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String token = (String) session.getAttribute("token");
    if (token == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
    User user = JWToken.validTokenAndGetUser(token);
    if (user == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
    String beforeNickname = user.getNickname();
    String nickname = request.getParameter("nickname");
    String address = request.getParameter("address");
    int age = Integer.parseInt(request.getParameter("age"));
    user.setNickname(nickname);
    user.setAddress(address);
    user.setAge(age);
    user = UserRepository.userUpdate(user, beforeNickname);
    if (user == null) {
%>
    <script>
        alert("회원 수정 실패");
        location.href = "userModify.jsp";
    </script>
<%
    } else {
        response.sendRedirect("mypage.jsp");
    }
%>
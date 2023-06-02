<%@ page import="com.example.potejsp.login.UserDAO" %>
<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.APIUser" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    APIUser apiUser = (APIUser) session.getAttribute("apiUser");
    if (apiUser == null) {
        response.sendRedirect("index.jsp");
        return ;
    }
    session.removeAttribute("apiUser");
    String email = request.getParameter("email");
    String nickname = request.getParameter("nickname");
    String address = request.getParameter("address");
    String naverId = request.getParameter("naverId");
    int age = Integer.parseInt(request.getParameter("age"));
    User newUser = new User();
    newUser.setEmail(email);
    newUser.setNickname(nickname);
    newUser.setAddress(address);
    newUser.setAge(age);
    newUser.setNaverId(naverId);
    newUser = UserDAO.userInsert(newUser);
    if (newUser.getId() == 0) {
%>
    <script>
        alert("회원가입 실패");
        location.href = "join.jsp";
    </script>
<%
    } else {
        session.setAttribute("token", JWToken.getToken(newUser));
        response.sendRedirect("main.jsp");
    }
%>
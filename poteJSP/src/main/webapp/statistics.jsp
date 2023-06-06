<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
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
<html lang="en">
<head>
    <title>Statistics Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Statistics Page</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="main.jsp">메인<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="mypage.jsp">내정보</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#">통계보기</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container mt-5 mb-5">
    <div class="row">
        <div class="col-md-6">
            <!-- Your content goes here -->
        </div>
        <div class="col-md-6">
            <!-- Your content goes here -->
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
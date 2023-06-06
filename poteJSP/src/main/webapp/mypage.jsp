<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.login.UserDAO" %>
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
            <li class="nav-item active">
                <a class="nav-link" href="#">내정보</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="statistics.jsp">통계보기</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container mt-5 mb-5">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">회원 정보</h5>
                    <table class="table table-bordered">
                        <tr>
                            <th style="text-align:center">닉네임</th>
                            <td style="text-align:center"><%= user.getNickname() %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">주소</th>
                            <td style="text-align:center"><%= user.getAddress() %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">나이</th>
                            <td style="text-align:center"><%= user.getAge() %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">이메일</th>
                            <td style="text-align:center"><%= user.getEmail() %></td>
                        </tr>
                        <tr>
                            <th ></th>
                            <td style="text-align:center">
                                <button onclick="location='userModify.jsp'">수정하기</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">개인 통계</h5>
                    <table class="table table-bordered">
                        <tr>
                            <th style="text-align:center">투표 만든 횟수</th>
                            <td style="text-align:center"><%= UserDAO.selectMakeCountByUserNickname(user.getNickname()) %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">투표 참여 횟수</th>
                            <td style="text-align:center"><%= UserDAO.selectVoteCountByUserId(user.getId()) %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">선정된 횟수</th>
                            <td style="text-align:center"><%= UserDAO.countMyChoiceResultByUserId(user.getId()) %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">가장 많이 고른 매뉴</th>
                            <td style="text-align:center"><%= UserDAO.selectItemNameByUserId(user.getId()) %></td>
                        </tr>
                        <tr>
                            <th style="text-align:center">가장 많이 선정된 매뉴</th>
                            <td style="text-align:center"><%= UserDAO.selectChoiceNameByUserId(user.getId()) %></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
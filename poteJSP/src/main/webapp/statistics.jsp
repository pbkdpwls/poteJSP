<%@ page import="com.example.potejsp.domain.User" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.repository.UserRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
    List<Integer> dataList = new ArrayList<>();
    for (int i = 0; i < 25; i++) {
        dataList.add(UserRepository.selectRegionCount(i)); // 실제 데이터를 넣으세요.
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Statistics Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">총 유저수</h5>
                    <h1 class="display-4"><%= UserDAO.countAllUser() %></h1>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">지역별 유저수</h5>
                        <canvas id="myChart" width="800" height="300"></canvas>
                        <script>
                            const labels = ['강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구', '금천구', '노원구', '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구', '성북구', '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구'];
                            const data = [<% for (int i = 0; i < dataList.size(); i++) { %><%= dataList.get(i) %><%= (i < dataList.size()-1) ? ", " : "" %><% } %>];

                            const ctx = document.getElementById('myChart').getContext('2d');
                            const myChart = new Chart(ctx, {
                                type: 'bar',
                                data: {
                                    labels: labels,
                                    datasets: [{
                                        label: '인원수',
                                        data: data,
                                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                        borderColor: 'rgba(75, 192, 192, 1)',
                                        borderWidth: 1
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        </script>
                    </div>
                </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">총 투표 개수</h5>
                    <h1 class="display-4"><%= UserDAO.countAllBoard() %></h1>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">총 투표 참여 횟수</h5>
                    <h1 class="display-4"><%= UserDAO.countAllVoter() %></h1>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">가장 많이 선정된 매뉴</h5>
                    <h1 class="display-4"><%= UserDAO.selectBestVoteResult() %></h1>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">가장 많이 투표된 매뉴</h5>
                    <h1 class="display-4"><%= UserDAO.selectBestChoiceResult() %></h1>
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
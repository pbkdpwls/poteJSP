<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
    <style>
        body{
            margin:0;
            padding:0;
            width:100%;
        }
        .header {
            height: 70px;
            width: 100%;
            background-color: #FFFFFF;
            box-sizing: border-box;
            padding-right: 20px;
        }

        .divider {
            height: 2px;
            width: 100%;
            background-color: #DBDBDB;
            margin-bottom: 50px;
        }

        .indexBody{
            width: 100%;
            margin: 0 auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .indexHeader{
            display: flex;
            flex-direction: row;
            margin-bottom: 10px;
        }

        .indexHeaderSearchInput{
            width: 500px;
            height: 30px;
            font-size: 15px;
            padding:10px;
            box-sizing: border-box;
            margin-right:180px;
        }

        .indexHeaderTitle{
            font-size: 30px;
            font-weight: bold;
            margin-right: 30px;
        }

        .indexHeaderFilter{
            fotn-size: 18px;
            font-weight: bold;
            color: dimgray;
        }

        .buttons {
            float: right;
            margin-top: 25px;
        }

        .buttons button {
            font-weight: bold;
            font-size: 18px;
            border: none;
            background: none;
            padding: 0;
            margin-right: 10px;
        }

        .loggedOut {
            display: none;
        }

        .loggedIn {
            display: none;
        }

        .component {
            width: 1000px;
            height: 160px;
            padding: 30px;
            box-sizing: border-box;
            background-color: #CDDBF6;
            border-radius: 10px;
            cursor: pointer;
            transition: background-color 0.5s;
        }

        .details {
            width: 1000px;
            display: none;
            background-color: #EBF0F9;
            padding: 10px;
            box-sizing: border-box;
        }

        .details .item div {
            width: 800px;
            margin: 10px;
            padding: 10px;
            box-sizing: border-box;
            background-color: #FFFFFF;
            font-weight: bold;
        }

        .btn {
            background-color: #CDDBF6;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            cursor: pointer;
            margin: 10px;
            font-weight: bold;
        }
    </style>
</head>
<script>
    var condition = 2; // 조건 변수, 1 또는 2로 설정

    window.onload = function() {
        if (condition === 1) {
            document.querySelector('.loggedOut').style.display = 'block';
        } else if (condition === 2) {
            document.querySelector('.loggedIn').style.display = 'block';
        }
    };

    function toggleDetails() {
        var details = document.getElementById("details");
        details.style.display = (details.style.display === "none") ? "block" : "none";
    }

    function isVoteChecked(){
        var voteCheckBox = document.getElementById("isVoted");
        if(voteCheckBox.checked==true){

        }else{

        }
    }
</script>
<body>
<div class="header">
    <div class="buttons loggedOut">
        <button>로그인</button>
        <button>회원가입</button>
    </div>
    <div class="buttons loggedIn">
        <button onclick="location='voteGenerate.jsp'">투표생성</button>
        <button>로그아웃</button>
    </div>
</div>
<div class="divider"></div>

<div class="indexBody">
    <div class="indexHeader">
        <div class="indexHeaderTitle">투표 목록</div>
        <input type="text" name="searchInput" id="searchInput" class="indexHeaderSearchInput">
        <div class="indexHeaderFilter">
            진행중인 투표만 보기
            <label>
                <input type="checkbox" id="isVoted" name="isVoted" onclick="isVoteChecked()">
            </label>
        </div>
    </div>

    <div class="component" onclick="toggleDetails()">
        <div style="font-weight: bold; font-size: 30px; margin-top:20px">VOTE 팀 야식 메뉴</div>
        <div style="font-weight: bold; font-size: 17px; margin-top:5px">06.01 15:00 / 서울 / 오세훈</div>
    </div>
    <div class="details" id="details">
        <% String[] menu = {"Menu 1", "Menu 2", "Menu 3"}; %>
        <div class="item">
            <% for (String item : menu) { %>
            <div><%= item %></div>
            <% } %>
        </div>
        <div class="btn" onclick="toggleDetails()">확인</div>
    </div>

</div>
</body>
</html>

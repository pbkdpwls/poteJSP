<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Component Example</title>
    <style>
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
    <script>
        function toggleDetails() {
            var details = document.getElementById("details");
            details.style.display = (details.style.display === "none") ? "block" : "none";
        }
    </script>
</head>
<body>
<div class="component" onclick="toggleDetails()">
    <div style="font-weight: bold; font-size: 30px; margin-top:20px">VOTE 팀 야식 메뉴</div>
    <div style="font-weight: bold; font-size: 17px; margin-top:5px">06.01 15:00  서울</div>
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
</body>
</html>

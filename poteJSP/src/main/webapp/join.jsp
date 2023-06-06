<%@ page import="com.example.potejsp.login.APIUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            background: #f6f5f7;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            font-family: 'Montserrat', sans-serif;
            height: 100vh;
            margin: -20px 0 50px;
        }

        h2 {
            font-weight: bold;
            margin: 0;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        input[type="text"],
        input[type="password"] {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            width: 300px;
        }

        select {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            width: 300px;
            height: 40px;
        }

        button {
            border-radius: 20px;
            border: 1px solid #CDDBF6;
            background-color: #CDDBF6;
            color: #FFFFFF;
            font-size: 12px;
            font-weight: bold;
            padding: 12px 45px;
            letter-spacing: 1px;
            text-transform: uppercase;
            transition: transform 80ms ease-in;
            cursor: pointer;
        }

        button:active {
            transform: scale(0.95);
        }

        button:focus {
            outline: none;
        }
    </style>
</head>
<body>
    <h2>회원가입</h2>
    <form action="insert.jsp" method="post" accept-charset="UTF-8">
        <input type="text" id="email" name="email" value="<%=apiUser.getEmail()%>" readonly>
        <input type="text" id="nickname" name="nickname" placeholder="Nickname">
        <select name="address" id="address">
            <option value="강남구">강남구</option>
            <option value="강동구">강동구</option>
            <option value="강북구">강북구</option>
            <option value="강서구">강서구</option>
            <option value="관악구">관악구</option>
            <option value="광진구">광진구</option>
            <option value="구로구">구로구</option>
            <option value="금천구">금천구</option>
            <option value="노원구">노원구</option>
            <option value="도봉구">도봉구</option>
            <option value="동대문구">동대문구</option>
            <option value="동작구">동작구</option>
            <option value="마포구">마포구</option>
            <option value="서대문구">서대문구</option>
            <option value="서초구">서초구</option>
            <option value="성동구">성동구</option>
            <option value="성북구">성북구</option>
            <option value="송파구">송파구</option>
            <option value="양천구">양천구</option>
            <option value="영등포구">영등포구</option>
            <option value="용산구">용산구</option>
            <option value="은평구">은평구</option>
            <option value="종로구">종로구</option>
            <option value="중구">중구</option>
            <option value="중랑구">중랑구</option>
        </select>
        <input type="password" id="naverId" name="naverId" value="<%=apiUser.getNaverId()%>" readonly>
        <input type="text" id="age" name="age" placeholder="Age">
        <button type="submit" class="insert-button">가입</button>
</form>
</body>
</html>


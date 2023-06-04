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

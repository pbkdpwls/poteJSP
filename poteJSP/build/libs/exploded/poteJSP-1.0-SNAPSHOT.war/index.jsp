<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="com.example.potejsp.login.NaverAPI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String token = (String) session.getAttribute("token");
    if (token != null) {
        if (JWToken.isValidToken(token)) {
%>
<jsp:forward page="main.jsp" />
<%
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>네이버 로그인</title>
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

        h1 {
            font-weight: bold;
            margin: 0;
        }

        p {
            font-size: 14px;
            font-weight: 100;
            line-height: 20px;
            letter-spacing: 0.5px;
            margin: 20px 0 30px;
        }

        a {
            color: #333;
            font-size: 14px;
            text-decoration: none;
            margin: 15px 0;
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
        }

        button:active {
            transform: scale(0.95);
        }

        button:focus {
            outline: none;
        }

        button.ghost {
            background-color: transparent;
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
            position: relative;
            overflow: hidden;
            width: 100%;
            max-width: 768px;
            min-height: 480px;
        }

        .overlay {
            background: #CDDBF6;
            background: -webkit-linear-gradient(to right, #6478B3, #CDDBF6);
            background: linear-gradient(to right, #6478B3, #CDDBF6);
            background-repeat: no-repeat;
            background-size: cover;
            background-position: 0 0;
            color: #FFFFFF;
            position: relative;
            left: -100%;
            height: 100%;
            width: 200%;
            transform: translateX(0);
            transition: transform 0.6s ease-in-out;
        }

        .container.right-panel-active .overlay {
            transform: translateX(50%);
        }

        .overlay-panel {
            position: absolute;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 0 40px;
            text-align: center;
            top: 0;
            height: 100%;
            width: 50%;
            transform: translateX(0);
            transition: transform 0.6s ease-in-out;
        }

        .container.right-panel-active .overlay-left {
            transform: translateX(0);
        }

        .overlay-right {
            right: 0;
            transform: translateX(0);
        }

        .container.right-panel-active .overlay-right {
            transform: translateX(20%);
        }
    </style>
</head>
<body>
<%
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + NaverAPI.clientId;
    apiURL += "&redirect_uri=" + NaverAPI.redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
%>
<div class="container" id="container">
    <div class="overlay">
        <div class="overlay-panel overlay-right">
            <h1>야식 투표 시스템</h1>
            <p>아래 버튼을 누르고 Naver 소셜 로그인을 진행하세요.</p>
            <button class="ghost" id="signUp" onclick="window.location.href='<%=apiURL%>'">
                Naver Login
            </button>
        </div>
    </div>
</div>
</body>
</html>

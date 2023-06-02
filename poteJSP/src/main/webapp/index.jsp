<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="com.example.potejsp.login.NaverAPI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
<html>
<head>
    <title>네이버로그인</title>
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
<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
</body>
</html>
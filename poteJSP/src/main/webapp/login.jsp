<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.potejsp.login.*" %>
<%@ page import="com.example.potejsp.domain.APIUser" %>
<%@ page import="com.example.potejsp.domain.User" %>
<%@ page import="com.example.potejsp.repository.UserRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>네이버로그인</title>
</head>
<body>
<%
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String apiURL;
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    apiURL += "client_id=" + NaverAPI.clientId;
    apiURL += "&client_secret=" + NaverAPI.clientSecret;
    apiURL += "&redirect_uri=" + NaverAPI.redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    String access_token = "";
    String refresh_token = "";
    try {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            throw new RuntimeException("API 요청과 응답에 실패했습니다. : " + responseCode);
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(res.toString(), Map.class);
        access_token = (String) map.get("access_token");
        refresh_token = (String) map.get("refresh_token");
        APIUser apiUser = NaverAPI.getProfile(access_token);
        if (apiUser == null) {
            out.println("유저 정보를 가져오지 못했습니다.");
            return ;
        }
        User user = UserRepository.userSelectByEmailAndNaverId(apiUser.getEmail(), apiUser.getNaverId());
        if (user == null) {
            session.setAttribute("apiUser", apiUser);
            response.sendRedirect("join.jsp");
            return;
        }
        session.setAttribute("token", JWToken.getToken(user));
        response.sendRedirect("main.jsp");
    } catch (Exception e) {
        out.println(e);
        return ;
    }
%>
</body>
</html>
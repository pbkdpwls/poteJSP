<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>투표 폼 생성</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 30px;
            padding: 30px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            margin-top: 50px;
        }

        h2 {
            text-align: center;
            color: #333333;
        }

        form {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #666666;
            font-weight: bold;
        }

        input[type="text"],
        input[type="datetime-local"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #cccccc;
            border-radius: 5px;
            font-size: 14px;
            margin-bottom: 10px;
        }

        select {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            width: 100%;
            height: 40px;
        }

        .menu-item {
            margin-bottom: 10px;
        }

        button {
            padding: 10px 20px;
            background-color: #333333;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button[type="submit"] {
            background-color: #CBED94;
        }

        button[type="button"] {
            background-color: #cccccc;
            margin-left: 10px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            var maxMenuCount = 5; // 최대 메뉴 항목 수
            var menuCount = 3; // 초기 메뉴 항목 수

            // 추가 메뉴 항목 생성
            $('#addMenuButton').click(function() {
                if (menuCount < maxMenuCount) {
                    var menuIndex = menuCount + 1;
                    var newMenu =
                        '<div class="menu-item">' +
                        '    <label for="menu' + menuIndex + '">메뉴 ' + menuIndex + '</label>' +
                        '    <input type="text" name="menu' + menuIndex + '" id="menu' + menuIndex + '" required>' +
                        '</div>';
                    $('#menuContainer').append(newMenu);
                    menuCount++;

                    if (menuCount === maxMenuCount) {
                        $('#addMenuButton').prop('disabled', true);
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h2>투표 생성</h2>
    <form action="createVote.jsp" method="POST" accept-charset="UTF-8">
        <div>
            <label for="title">제목</label>
            <input type="text" name="title" id="title" required>
        </div>
        <div>
            <label for="deadline">마감 기한</label>
            <input type="datetime-local" name="deadline" id="deadline" required>
        </div>
        <div>
            <label for="address">지역</label>
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
        </div>
        <div id="menuContainer">
            <div class="menu-item">
                <label for="menu1">메뉴 1</label>
                <input type="text" name="menu1" id="menu1" required>
            </div>
            <div class="menu-item">
                <label for="menu2">메뉴 2</label>
                <input type="text" name="menu2" id="menu2" required>
            </div>
            <div class="menu-item">
                <label for="menu3">메뉴 3</label>
                <input type="text" name="menu3" id="menu3" required>
            </div>
        </div>
        <br>
        <button type="button" id="addMenuButton">항목 추가하기</button>
        <br><br>
        <button type="submit">생성하기</button>
        <button type="button" onclick="history.back()">취소하기</button>
    </form>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <form action="index.jsp" method="POST">
        <div>
            <label for="title">제목</label>
            <input type="text" name="title" id="title" required>
        </div>
        <div>
            <label for="deadline">마감 기한</label>
            <input type="datetime-local" name="deadline" id="deadline" required>
        </div>
        <div>
            <label for="location">지역</label>
            <input type="text" name="location" id="location" required>
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
        <button type="button">취소하기</button>
    </form>
</div>
</body>
</html>

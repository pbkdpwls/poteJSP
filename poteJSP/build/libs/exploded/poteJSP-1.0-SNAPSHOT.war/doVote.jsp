<%@ page import="com.example.potejsp.repository.VoterRepository" %>
<%@ page import="com.example.potejsp.login.JWToken" %>
<%@ page import="com.example.potejsp.login.User" %>
<%@ page import="com.example.potejsp.repository.BoardRepository" %>
<%@ page import="com.example.potejsp.repository.ItemRepository" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%! User user = null; %>
<%
    String token = (String) session.getAttribute("token");
    if (token == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    user = JWToken.validTokenAndGetUser(token);
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Vote</title>
</head>
<body>
<%
    int user_id = user.getId();

    // 폼에서 값 받아오기
    String item_id_param = request.getParameter("item_id");
    String board_id_param = request.getParameter("board_id");
    System.out.println(item_id_param);
    if (item_id_param == null || board_id_param == null) {
%>
<script>
    alert("( ˃̣̣̥᷄⌓˂̣̣̥᷅ ) : 투표할 항목을 선택해주세요.");
    window.location.replace("main.jsp");
</script>
<%
        return;
    }

    int item_id = Integer.parseInt(item_id_param);
    int board_id = Integer.parseInt(board_id_param);

    VoterRepository voterRepository = new VoterRepository();
    BoardRepository boardRepository = new BoardRepository();
    ItemRepository itemRepository = new ItemRepository();
    int result = 0;
    // 주소 검사
    int valAddr = voterRepository.validateAddress(board_id, user_id);
    System.out.println("valadrr : " + valAddr);
    if (valAddr > 0) {
        // 권한 검사(중복투표 방지)
        int val = voterRepository.validateVoter(0, user_id, item_id, board_id);
        System.out.println(val);
        if (val > 0) {

            // isProgressed 체크
            if (boardRepository.selectIsProgressed(board_id) == 0) {
                response.sendRedirect("index.jsp");
            } else {
                // 투표 실행
                result = voterRepository.vote(user_id, item_id);

                int voterCount = boardRepository.selectVoterCount(board_id); //해당 board의 투표수 조회
                int usersCount = boardRepository.selectUsersCount(board_id); //해당 board의 usersCount 조회

                if(voterCount >= usersCount) {  //인원수 다 채워지면
                    boardRepository.updateIsProgressed(board_id);   //투표 못하게 isProgressed 0으로 처리


                    HashMap<String, Integer> voteCountResult = itemRepository.getVoteCount(board_id); // 아이템 이름당 투표수 구하기

                    int maxVoteCount = 0;
                    String mostVotedItem = null;
                    int count = 0;

                    // 최다 투표수 찾기
                    for (Map.Entry<String, Integer> entry : voteCountResult.entrySet()) {
                        int voteCount = entry.getValue();
                        if (voteCount > maxVoteCount) {
                            maxVoteCount = voteCount;
                            count = 1; // 최다 투표수 변경 시 count 초기화
                        } else if (voteCount == maxVoteCount) {
                            count++; // 동점인 아이템 개수 증가
                        }
                    }

                    // 동점인 경우 랜덤하게 아이템 선택
                    if (count > 1) {
                        Random random = new Random();
                        int randomIndex = random.nextInt(count);

                        int currentIndex = 0;
                        for (Map.Entry<String, Integer> entry : voteCountResult.entrySet()) {
                            String itemName = entry.getKey();
                            int voteCount = entry.getValue();
                            if (voteCount == maxVoteCount) {
                                if (currentIndex == randomIndex) {
                                    mostVotedItem = itemName;
                                    break;
                                }
                                currentIndex++;
                            }
                        }
                    } else {
                        // 동점이 아닌 경우, 최다 투표수를 받은 아이템 이름 추출
                        for (Map.Entry<String, Integer> entry : voteCountResult.entrySet()) {
                            String itemName = entry.getKey();
                            int voteCount = entry.getValue();
                            if (voteCount == maxVoteCount) {
                                mostVotedItem = itemName;
                                break;
                            }
                        }
                    }

                    boardRepository.updateVoteResult(board_id, mostVotedItem);  //투표결과 입력


                }
            }



            if (result > 0) {
%>
<script>
    alert("(｡˃ ᵕ ˂ )b : 투표완료!!!");
    window.location.replace("main.jsp"); // main으로, 히스토리가 남지 않음.(뒤로가기 해도 doVote로 안감)
</script>
<%
    return;
} else {
%>
<script>
    alert("서버에 접속할 수 없습니다."); // DB Insert 실패
    window.location.replace("main.jsp");
</script>
<%
        return;
    }
} else {
    System.out.println("중복 투표 - 권한 없음");
%>
<script>
    alert("(;´・`)> : 이미 투표한 항목입니다.");
    window.location.replace("main.jsp");
</script>
<%
    }
} else {
    System.out.println("주소 일치 X");
%>
<script>
    alert("(˃̣̣̣̣︿˂̣̣̣̣ ) : 같은 주소 내 투표만 진행할 수 있어요.");
    window.location.replace("main.jsp");
</script>
<%
    }
%>
</body>
</html>

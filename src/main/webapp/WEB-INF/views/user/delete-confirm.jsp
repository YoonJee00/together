<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
    <title>회원 탈퇴</title>
    <style>
        /* 중앙 정렬 */
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        /* 버튼 간격 */
        button {
            margin: 0 10px;
        }

        /* footer 스타일 */
        #footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 50px; /* 적절한 높이로 수정해주세요 */
            background-color: lightgray;
        }
    </style>
</head>
<body>
<%@ include file="../layout/header.jsp"%>

<h1>정말로 회원 탈퇴를 하시겠습니까?</h1>
<form action="/user/${Id}/delete" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" name="confirm" value="yes"/>
    <div>
        <button type="submit">예</button>
        <button type="button" onclick="history.back()">아니오</button>
    </div>
</form>

<div id="footer">
    <%@ include file="../layout/footer.jsp"%>
</div>
</body>
</html>
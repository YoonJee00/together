<!-- user/delete-confirm.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 탈퇴</title>
</head>
<body>
<h1>정말로 회원 탈퇴를 하시겠습니까?</h1>
<form action="/user/${Id}/delete" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" name="confirm" value="yes"/>
    <button type="submit">예</button>
    <button type="button" onclick="history.back()">아니오</button>
</form>
</body>
</html>

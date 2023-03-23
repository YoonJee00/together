<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
</head>
<body>
<form class="login__input" action ="/auth/signup" method="post">
<input type="text" name="username" placeholder="유저네임" required="required" maxlength="20"/>
<input type="password" name="password" placeholder="패스워드" required="required"/>
<input type="email" name="email" placeholder="이메일" required="required"/>
<input type="text" name="name" placeholder="이름" required="required"/>
<button>가입<button>
</form>
</body>
</html>
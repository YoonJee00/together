<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Together</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
    <style>
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
    <div class="container">
        <main class="loginMain">
        <!--로그인섹션-->
            <section class="login">
               <!--로그인박스-->
                <article class="login__form__container">
                   <!--로그인 폼-->
                   <div class="login__form">
                        <h1>회원 탈퇴</h1>
                        <span style="color: #C10E0E; font-size: 10pt;">회원 탈퇴 후에는 되돌릴 수 없습니다.</span>

                        <!--로그인 인풋-->
                        <form class="login__input"  action="/user/${Id}/delete" method="POST">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="hidden" name="confirm" value="yes"/>
                            <input type="password" name="password" placeholder="비밀번호 입력" required="required" />
                            <input type="password" name="password" placeholder="비밀번호 확인" required="required" />
                            <div style="display: flex; justify-content: space-around; width: 100%; margin: 0 auto;">
                              <button type="submit" style="width: 40%; height: 30px;">탈퇴하기</button>
                              <button type="button" style="width: 40%; height: 30px;" onclick="history.back()">뒤로가기</button>
                            </div>
                        </form>
                        <div class="login__horizon">
                        </div>
                    </div>
                </article>
            </section>
        </main>

    </div>
    <div id="footer">
        <%@ include file="../layout/footer.jsp"%>
    </div>
</body>

</html>
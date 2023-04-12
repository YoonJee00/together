<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>유저 검색</title>
    <style>
        /* 중앙 정렬 */
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 60vh;
        }

        /* 버튼 간격 */
        button {
            margin: 0 5px;
        }

        /* footer 스타일 */
        #footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 50px; /* 적절한 높이로 수정해주세요 */
            background-color: lightgray;
        }

      /* 기본 링크 스타일 (검은색) */
      a {
        color: #000;
        text-decoration: none;
      }

      /* 방문한 링크 스타일 (검은색) */
      a:visited {
        color: #000;
      }

      /* 클릭한 링크 스타일 (검은색) */
      a:active {
        color: #000;
      }

      /* 호버(마우스를 올렸을 때) 링크 스타일 (검은색) */
      a:hover {
        color: #FF8C00;
      }

      .profile-image {
              width: 50px; /* 원하는 최대 너비 설정 */
              height: 50px; /* 원하는 최대 높이 설정 */
              border-radius: 50%; /* 동그랗게 만들기 */
       }
    </style>

</head>
<body>
<%@ include file="../layout/header.jsp"%>

<h2>유저 검색</h2>
   <div>
      <form action="/members/memberList" method="GET">
         <input type="text" name="keyword" placeholder="유저 정보를 입력해주세요">
         <button type="submit">검색</button>
      </form>
   </div>
   <table>
      <c:forEach items="${members}" var="member">
       <tr>
            <td><a href="/user/${member.id}"><img class="profile-image" src="/upload/${member.profileImageUrl}"
                   onerror="this.src='/images/person.png'" /></td>
           <td><a href="/user/${member.id}">${member.username}</a></td>
           <td><a href="/user/${member.id}">${member.name}</a></td>
       </tr>
      </c:forEach>
   </table>

   <div id="footer">
        <%@ include file="../layout/footer.jsp"%>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>글 검색</title>
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
    </style>

</head>
<body>
<%@ include file="../layout/header.jsp"%>

<h2>글 검색</h2>
	<div>
		<form action="/members/postList" method="GET">
			<input type="text" name="keyword" placeholder="글 내용을 입력해주세요">
			<button type="submit">검색</button>
		</form>
	</div>
	<table>
		<tr>
			<th>내용</th>
		</tr>
		<c:forEach items="${posts}" var="post">
			<tr>
				<td><a href="/image/${post.caption}">${post.caption}</a></td>
			</tr>
		</c:forEach>
	</table>
	<div id="footer">
        <%@ include file="../layout/footer.jsp"%>
    </div>
</body>
</html>
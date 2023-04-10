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

    <div>
        <button id="userSearchButton">유저 검색</button>
    </div>

    <div>
        <button id="writingsSearchButton">글 검색</button>
    </div>

	<div id="footer">
        <%@ include file="../layout/footer.jsp"%>
    </div>
</body>
</html>
<script src="/js/search.js"></script>
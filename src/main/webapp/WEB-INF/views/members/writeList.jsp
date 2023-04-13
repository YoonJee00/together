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

		button {
			background-color: #FFA500;
			border: none;
			color: #ffffff;
			cursor: pointer;
			display: inline-block;
			font-size: 1em;
			font-size: 15px;
			line-height: 1em;
			margin: 10px 10px;
			outline: none;
			padding: 12px 40px 10px;
			position: relative;
			text-transform: uppercase;
			font-weight: 700;
		}
		button:before,
		button:after {
			border-color: transparent;
			-webkit-transition: all 0.25s;
			transition: all 0.25s;
			border-style: solid;
			border-width: 0;
			content: "";
			height: 24px;
			position: absolute;
			width: 24px;
		}
		button:before {
			border-color: #FFA500;
			border-right-width: 2px;
			border-top-width: 2px;
			right: -5px;
			top: -5px;
		}
		button:after {
			border-bottom-width: 2px;
			border-color: #FFA500;
			border-left-width: 2px;
			bottom: -5px;
			left: -5px;
		}
		button:hover,
		button.hover {
			background-color: #FFA500;
		}
		button:hover:before,
		button.hover:before,
		button:hover:after,
		button.hover:after {
			height: 100%;
			width: 100%;
		}
    </style>

</head>
<body>
<%@ include file="../layout/header.jsp"%>

<h2>글 검색</h2>
	<div>
		<form action="/members/writeList" method="GET">
			<input type="text" name="keyword" placeholder="글 내용을 입력해주세요" style="width: 200px; height: 30px">
			<button type="submit">검색</button>
		</form>
	</div>
	<table>
		<c:forEach items="${writes}" var="write">
		<tr>
			<td><a href="/image/${write.imageId}">${write.caption}</a></td>
		</tr>
		</c:forEach>
	</table>
	<div id="footer">
        <%@ include file="../layout/footer.jsp"%>
    </div>
</body>
</html>
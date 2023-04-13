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
        body{
            text-align:center;
        }
        body:before{
            content:'';
            height:100%;
            display:inline-block;
            vertical-align:middle;
        }
        button{
            background:#FFA500;
            color:#fff;
            border:none;
            position:relative;
            height:60px;
            width: 250px;
            font-size:1.6em;
            padding:0 2em;
            cursor:pointer;
            transition:800ms ease all;
            outline:none;
        }
        button:hover{
            background:#fff;
            color:#FFA500;
        }
        button:before,button:after{
            content:'';
            position:absolute;
            top:0;
            right:0;
            height:2px;
            width:0;
            background: #FFA500;
            transition:400ms ease all;
        }
        button:after{
            right:inherit;
            top:inherit;
            left:0;
            bottom:0;
        }
        button:hover:before,button:hover:after{
            width:100%;
            transition:800ms ease all;
        }

        /* footer 스타일 */
        #footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 50px; /* 적절한 높이로 수정해주세요 */
            background-color: lightgray;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* 화면 높이에 따라 조절 */
        }

        .button-wrapper {
            margin: 10px; /* 버튼 간격 조절 */
        }
    </style>
</head>
<body>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <div class="button-wrapper">
        <button id="userSearchButton">유저 검색</button>
    </div>
    <div class="button-wrapper">
        <button id="writingsSearchButton">글 검색</button>
    </div>
</div>

<div id="footer">
    <%@ include file="../layout/footer.jsp"%>
</div>
</body>
</html>
<script src="/js/search.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="story.css">
<%@ include file="../layout/head.jsp"%>


<main class="main">
	<section class="container">
		<article class="story-list">
			<div class="story-list__item">
				<div class="sl__item__header">
				    <a href="/user/${detailDto.id}">
					    <div>
						    <img class="profile-image" src="/upload/${detailDto.profileImage}"
							    onerror="this.src='/images/person.png'" />
					    </div>
					</a>
					<div>${detailDto.name}</div>

					<c:if test="${detailDto.id eq principal.user.id}">
						<form action="/image/${imageId}/delete" method="post">
							<input type="hidden" name="_csrf" value="${_csrf.token}"/>
							<input type="hidden" name="confirm" value="yes"/>
							<div style="display:flex; justify-content:flex-end;">
								<button type="submit">삭제</button>
							</div>
						</form>
					</c:if>

				</div>

				<div class="sl__item__img">
					<img src="/upload/${detailDto.postImage}" />
				</div>

				<div class="sl__item__contents">
					<div class="sl__item__contents__icon">
						<button>
					        <c:choose>
						        <c:when test="${detailDto.likeState}">
						        	<i class="fas fa-heart active" id="LikeIcon-${detailDto.imageId}" onclick="toggleLike(${detailDto.imageId})"></i>
						        </c:when>
						        <c:otherwise>
								    <i class="far fa-heart" id="LikeIcon-${detailDto.imageId}" onclick="toggleLike(${detailDto.imageId})"></i>
						        </c:otherwise>
					        </c:choose>
						</button>
					</div>

					<span class="like"><b id="LikeCount-${detailDto.imageId}">${detailDto.likeCount} </b>likes</span>

					<div class="sl__item__contents__content">
						<p>${detailDto.caption}</p>
					</div>

					<div id="storyCommentList-${detailDto.imageId}">
					    <c:forEach var="comment" items="${detailDto.comments}">
					        <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
					        <p><b>${comment.user.name} :</b> ${comment.content}</p>

					            <c:if test="${principal.id eq comment.user.id}">
					                <button onclick="deleteComment(${comment.id}, ${comment.user.id})"><i class="fas fa-times"></i></button>
					             </c:if>
					        </div>
					    </c:forEach>
					</div>

					<div class="sl__item__input">
					    <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${detailDto.imageId}"/>
					    <button type="button" onclick="addComment(${detailDto.imageId})">게시</button>
					</div>
				</div>
			</div>

		</article>
	</section>
</main>
<script src="/js/profile.js"></script>
<script src="/js/detail.js"></script>
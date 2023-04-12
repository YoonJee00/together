<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<section class="container">
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">

			<!--스토리 아이템-->

			<!--스토리 아이템 끝-->

		</article>

		<div class="story-status">
			<div class="story-profile">
				<table>
					<tr>
						<td>
							<img class="profile-image" src="/upload/${principal.user.profileImageUrl}" onclick="location.href='/user/${principal.user.id}'"
								 onerror="this.src='/images/person.png'" />
						</td>
						<td>
							<div class="story-status-name pointer" onclick="location.href='/user/${principal.user.id}'">
								${principal.user.name}
							</div>
							<div class="story-status-bio">${principal.user.bio}</div>
						</td>
					</tr>
				</table>
			</div>
			<hr>
			<div class="story-status-others">
				<c:forEach var="subUser" items="${subUserList}">
					<div class="story-status-profile">
						<table>
							<tr>
								<td>
									<div onclick="location.href='/user/${subUser.id}'">
										<img class="profile-image" src="/upload/${subUser.profileImageUrl}"
										 onerror="this.src='/images/person.png'" />
									</div>
								</td>
								<td>
									<div class="story-status-name pointer" onclick="location.href='/user/${subUser.id}'">
											${subUser.name}
									</div>
									<div class="story-status-bio">${subUser.bio}</div>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</div>
		</div>

		<div class="notification-container" id="notification-container">
			<div class="notification-time">방금전</div>
			<table>
				<tr>
					<td width="30">
						<img class="profile-image" src="/upload/${subUser.profileImageUrl}"
							 onerror="this.src='/images/person.png'" />
					</td>
					<td>
						<div>코스</div>
					</td>
				</tr>
				<tr><td colspan="2"><div id="notification-content"></div></td></tr>
			</table>
		</div>

	</section>
</main>
<script src="/js/story.js"></script>
</body>
</html>
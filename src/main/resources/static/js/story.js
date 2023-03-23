// 현재 로그인한 유저의 ID
let principalId = $("#principalId").val();
console.log("로그인한 유저 : ", principalId)

// 스크롤 페이징을 위한 변수생성
let page = 0;

// (1) 스토리 로드하기
function storyLoad() {
    $.ajax({
        url: `/api/image?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("성공", res)

        res.data.content.forEach((image) => {
            let storyItem = getStoryItem(image);
            $("#storyList").append(storyItem);
        });
    }).fail(error => {
        console.log("실패", error)
    });
}

storyLoad();

function storyLoad2() {
    $.ajax({
        url: `/api/image/all?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log("성공", res)

        res.data.forEach((image) => {
            let storyItem = getStoryItem(image);
            $("#storyList").append(storyItem);
        });
    }).fail(error => {
        console.log("실패", error)
    });
}

storyLoad2();

function getStoryItem(image) {
	let item = `<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.username}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>
				<i class="fas fa-heart active" id="storyLikeIcon-1" onclick="toggleLike()"></i>
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-1">3 </b>likes</span>

		<div class="sl__item__contents__content">
			<p>사용자 코멘트입니다.</p>
		</div>

		<div id="storyCommentList-1">

			<div class="sl__item__contents__comment" id="storyCommentItem-1"">
				<p>
					<b>Lovely :</b> 상대방 코멘트입니다.
				</p>

				<button>
					<i class="fas fa-times"></i>
				</button>

			</div>

		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
			<button type="button" onClick="addComment()">게시</button>
		</div>

	</div>
</div>`
	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    if (checkNum < 1 && checkNum > -1) {
        page++;
        storyLoad();
    }
});


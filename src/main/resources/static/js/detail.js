function toggleLike(imageId) {
	let likeIcon = $(`#LikeIcon-${imageId}`);

	if (likeIcon.hasClass("far")) { // 좋아요
		// 좋아요 상태를 서버에 저장
		$.ajax({
			type: "post",
			url: `/api/image/${imageId}/likes`,
			dataType: "text"
		}).done(res=>{
			console.log(res);
			// Local Storage 에 좋아요 상태 저장
			localStorage.setItem(`likeState-${imageId}`, "true");

			let likeCountStr = $(`#LikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			$(`#LikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});

	} else { // 좋아요취소
		// 좋아요 상태를 서버에서 삭제
		$.ajax({
			type: "delete",
			url: `/api/image/${imageId}/likes`,
			dataType: "text"
		}).done(res=>{
			console.log(res);
			// Local Storage 에 좋아요 상태 저장
			localStorage.setItem(`likeState-${imageId}`, "false");

			let likeCountStr = $(`#LikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#LikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});
	}
}

// 페이지 로드 시 Local Storage 에서 좋아요 상태를 불러와서 초기 설정
$(document).ready(function() {
    $("[id^='LikeIcon-']").each(function() {
        let imageId = $(this).attr("id").split("-")[1];
        let likeState = localStorage.getItem(`likeState-${imageId}`);
        if (likeState === "true") {
            $(this).addClass("fas active").removeClass("far");
        } else {
            $(this).removeClass("fas active").addClass("far");
        }
    });
});


function addComment(imageId) {

   let commentInput = $(`#storyCommentInput-${imageId}`);
   let commentList = $(`#storyCommentList-${imageId}`);

   let data = {
      imageId: imageId,
      content: commentInput.val()
   }

   //console.log(data);
   //console.log(JSON.stringify(data));

   if (data.content === "") {
      alert("댓글을 작성해주세요!");
      return;
   }

   $.ajax({
      type: "post",
      url: "/api/comment",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json"
   }).done(res=>{
      //console.log("성공", res);

      let comment = res.data;

      let content = `
        <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
          <p>
            <b>${comment.user.username} :</b>
            ${comment.content}
          </p>
          <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
        </div>
      `;
      commentList.append(content);
   }).fail(error=>{
      console.log("오류", error.responseJSON.data.content);
      alert(error.responseJSON.data.content);
   });

   commentInput.val(""); // 인풋 필드를 깨끗하게 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {
   $.ajax({
      type: "delete",
      url: `/api/comment/${commentId}`,
      dataType: "json"
   }).done(res=>{
      console.log("성공", res);
      $(`#storyCommentItem-${commentId}`).remove();
   }).fail(error=>{
      console.log("오류", error);
   });
}


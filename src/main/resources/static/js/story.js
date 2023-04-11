/**
 2. 스토리 페이지
 (1) 스토리 로드하기
 (2) 스토리 스크롤 페이징하기
 (3) 좋아요, 안좋아요
 (4) 댓글쓰기
 (5) 댓글삭제
 */

// (0) 현재 로긴한 사용자 아이디
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
   $.ajax({
      url: `/api/image?page=${page}`,
      dataType: "json"
   }).done(res => {
      //console.log(res);
      res.data.content.forEach((image)=>{
         let storyItem = getStoryItem(image);
         $("#storyList").append(storyItem);
      });
      //로그인과 동시에 이벤트 등록
      notification();
   }).fail(error => {
      console.log("오류", error);
   });
}

//notification
function notification() {
   let eventSource = new EventSource("http://localhost:80/sub");

   eventSource.addEventListener("notification", function(event) {
      let message = event.data;

      let notification_content = document.getElementById('notification-content');
      let notification_container = document.getElementById('notification-container');

      notification_content.textContent = message;

      notification_container.classList.add('show');
      setTimeout(() => {
         notification_container.classList.remove('show');
      }, 2000);
   });

   eventSource.addEventListener("error", function(event) {
      eventSource.close();
   });
}

storyLoad();

function getStoryItem(image) {
   let item = `<div class="story-list__item">
   <div class="sl__item__header">
      <div>
         <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
              onerror="this.src='/images/person.png'" onclick="goToProfile(${image.user.id})"/>
      </div>
      <div>${image.user.name}</div>
   </div>

   <div class="sl__item__img">
      <img src="/upload/${image.postImageUrl}" />
   </div>

   <div class="sl__item__contents">
      <div class="sl__item__contents__icon">

         <button>`;

   if(image.likeState){
      item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
   }else{
      item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
   }


   item += `
         </button>
      </div>

      <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

      <div class="sl__item__contents__content">
         <p>${image.caption}</p>
      </div>

      <div id="storyCommentList-${image.id}">`;

   image.comments.forEach((comment)=>{
      item +=`<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
            <p>
               <b>${comment.user.name} :</b> ${comment.content}
            </p>`;

      if(principalId == comment.user.id){
         item += `   <button onclick="deleteComment(${comment.id})">
                              <i class="fas fa-times"></i>
                           </button>`;
      }

      item += `
         </div>`;

   });


   item += `
      </div>

      <div class="sl__item__input">
         <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
         <button type="button" onClick="addComment(${image.id})">게시</button>
      </div>

   </div>
</div>`;
   return item;
}

function goToProfile(userId) {
   location.href = `/user/${userId}`;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
   //console.log("윈도우 scrollTop", $(window).scrollTop());
   //console.log("문서의 높이", $(document).height());
   //console.log("윈도우 높이", $(window).height());

   let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
   //console.log(checkNum);

   if(checkNum < 1 && checkNum > -1){
      page++;
      storyLoad();
   }
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
   let likeIcon = $(`#storyLikeIcon-${imageId}`);

   if (likeIcon.hasClass("far")) { // 좋아요 하겠다

      $.ajax({
         type: "post",
         url: `/api/image/${imageId}/likes`,
         dataType: "json"
      }).done(res=>{

         localStorage.setItem(`likeState-${imageId}`, "true");

         let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
         let likeCount = Number(likeCountStr) + 1;
         $(`#storyLikeCount-${imageId}`).text(likeCount);

         likeIcon.addClass("fas");
         likeIcon.addClass("active");
         likeIcon.removeClass("far");
      }).fail(error=>{
         console.log("오류", error);
      });



   } else { // 좋아요취소 하겠다

      $.ajax({
         type: "delete",
         url: `/api/image/${imageId}/likes`,
         dataType: "json"
      }).done(res=>{

         localStorage.setItem(`likeState-${imageId}`, "false");

         let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
         let likeCount = Number(likeCountStr) - 1;
         $(`#storyLikeCount-${imageId}`).text(likeCount);

         likeIcon.removeClass("fas");
         likeIcon.removeClass("active");
         likeIcon.addClass("far");
      }).fail(error=>{
         console.log("오류", error);
      });


   }
}

// (4) 댓글쓰기
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
            <b>${comment.user.name} :</b>
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

/* 상태창, 알림창 스크롤 따라오기 */
$(document).ready(function() {
   let currentStatusPosition = parseInt($(".story-status").css("top"));
   let currentNotificationPosition = parseInt($(".notification-container").css("top"));

   $(window).scroll(function() {
      let position = $(window).scrollTop();
      $(".story-status").stop().animate({ "top": position + currentStatusPosition + "px" }, 500);
      $(".notification-container").stop().animate({ "top": position + currentNotificationPosition + "px" }, 500);
   });
});



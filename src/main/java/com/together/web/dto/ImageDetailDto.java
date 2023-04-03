package com.together.web.dto;

import com.together.domain.comment.Comment;
import com.together.domain.image.Image;
import lombok.Data;

import java.util.List;

@Data
public class ImageDetailDto {
    private int imageId;
    private String name;
    private String profileImage;
    private String caption;
    private String postImage;
    private int likeCount;
    private boolean likeState;
    private List<Comment> comments;

    public ImageDetailDto(Image image) {

        this.imageId = image.getId();
        this.name = image.getUser().getName();
        this.profileImage = image.getUser().getProfileImageUrl();
        this.caption = image.getCaption();
        this.postImage = image.getPostImageUrl();
        this.likeCount = image.getLikes().size();
        this.likeState = image.isLikeState();
        this.comments = image.getComments();
    }
}
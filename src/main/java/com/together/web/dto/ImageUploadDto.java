package com.together.web.dto;

import com.together.domain.image.Image;
import com.together.domain.user.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Builder
@Data
public class ImageUploadDto {
    private int imageId;
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user)
                .build();
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
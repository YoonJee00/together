package com.together.service;

import com.together.config.auth.PrincipalDetails;
import com.together.domain.comment.CommentRepository;
import com.together.domain.image.Image;
import com.together.domain.image.ImageRepository;
import com.together.domain.likes.LikesRepository;
import com.together.web.dto.ImageDetailDto;
import com.together.web.dto.ImageUploadDto;
import com.together.web.dto.WriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Image> 인기사진() {
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true)
    public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        images.forEach((image) -> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지 파일이름: " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);

    }

    @Transactional
    public ImageDetailDto detailImage(int imageId, int principalId) {
        Image image = imageRepository.imageDetail(imageId, principalId);
        ImageDetailDto imageDetailDto = new ImageDetailDto(image);

        return imageDetailDto;
    }

    @Transactional
    public void deleteImage(int imageId) {
        likesRepository.deleteAllByImageId(imageId);
        commentRepository.deleteAllByImageId(imageId);
        imageRepository.deleteById(imageId);
    }

    @Transactional
    public List<WriteDto> findWrite(String keyword) { //글검색
        List<Image> images = imageRepository.findWriteSearch(keyword);
        List<WriteDto> writeDtoList = new ArrayList<>();

        if (images.isEmpty()) return writeDtoList;

        for (Image image : images) {
            WriteDto post = WriteDto.builder()
                    .imageId(image.getId())
                    .caption(image.getCaption())
                    .build();
            writeDtoList.add(this.WriteEntity(image));
        }

        return writeDtoList;
    }

    @Transactional
    public WriteDto WriteEntity(Image image) {
        return WriteDto.builder()
                .imageId(image.getId())
                .caption(image.getCaption())
                .build();
    }
}
package com.together.service;

import com.together.domain.comment.CommentRepository;
import com.together.domain.image.ImageRepository;
import com.together.domain.likes.LikesRepository;
import com.together.domain.subscribe.SubscribeRepository;
import com.together.domain.user.User;
import com.together.domain.user.UserRepository;
import com.together.handler.ex.CustomApiException;
import com.together.handler.ex.CustomException;
import com.together.handler.ex.CustomValidationApiException;
import com.together.web.dto.UserDto;
import com.together.web.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final SubscribeRepository subscribeRepository;
    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            // throw -> return 으로 변경
            return new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    } // 더티체킹으로 업데이트 됨.

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, int principalId) {
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
//        System.out.println("=====================");
//        userEntity.getImages().get(0);

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        }); //프로필페이지 좋아요 카운트

        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user) {

        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을 수 없는 id입니다.");
        });

        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

    @Transactional
    public void deleteUserById(int id) {
        subscribeRepository.deleteAllByFromUserId(id);
        subscribeRepository.deleteAllByToUserId(id);
        imageRepository.deleteAllByUserId(id);
        likesRepository.deleteAllByUserId(id);
        commentRepository.deleteAllByUserId(id);

        userRepository.deleteById(id);
    }

    @Transactional
    public List<UserDto> findMember(String keyword){ //회원 조회,검색
        List<User> users = userRepository.findAllSearch(keyword);
        List<UserDto> userDtoList = new ArrayList<>();

        if(users.isEmpty()) return userDtoList;

        for(User user : users) {
            userDtoList.add(this.convertEntity(user));
        }

        return userDtoList;
    }

    @Transactional
    private UserDto convertEntity(User user) {
        return UserDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}
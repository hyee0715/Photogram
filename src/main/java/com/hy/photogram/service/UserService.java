package com.hy.photogram.service;

import com.hy.photogram.domain.subscribe.SubscribeRepository;
import com.hy.photogram.domain.user.User;
import com.hy.photogram.domain.user.UserRepository;
import com.hy.photogram.handler.ex.CustomApiException;
import com.hy.photogram.handler.ex.CustomException;
import com.hy.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /* 사용자 프로필 이미지 저장 폴더 */
    @Value("${file.path}")
    private String uploadFolder;

    /* 프로필 검색 */
    public List<User> search(String name) {
        return userRepository.findByNameContaining(name);
    }

    /* 회원정보 수정 */
    @Transactional
    public User update(Long id, User user) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomException("회원정보 수정 실패: 회원 ID를 찾을 수 없습니다.");
        });

        userEntity.setName(user.getName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

    /* 사용자 프로필 */
    @Transactional(readOnly = true)
    public UserProfileDto profile(Long pageUserId, Long principalUserId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            return new CustomException("프로필 페이지가 존재하지 않습니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalUserId);
        userProfileDto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.subscribeState(principalUserId, pageUserId);
        int subscribeCount = subscribeRepository.subscribeCount(pageUserId);

        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);

        userEntity.getImages().forEach((image) -> {
            image.setLikesCount(image.getLikes().size());
        });

        return userProfileDto;
    }

    /* 사용자 프로필 이미지 변경 */
    @Transactional
    public User profileImageUpdate(Long principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            return new CustomApiException("찾을 수 없는 사용자입니다.");
        });

        userEntity.setProfile_image_url(imageFileName);

        return userEntity;
    }
}

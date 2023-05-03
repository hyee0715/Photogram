package com.hy.photogram.service;

import com.hy.photogram.domain.user.User;
import com.hy.photogram.domain.user.UserRepository;
import com.hy.photogram.handler.ex.CustomException;
import com.hy.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional(readOnly = true)
    public UserProfileDto profile(Long pageUserId, Long principalUserId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            return new CustomException("프로필 페이지가 존재하지 않습니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalUserId);
        userProfileDto.setImageCount(userEntity.getImages().size());

        return userProfileDto;
    }
}

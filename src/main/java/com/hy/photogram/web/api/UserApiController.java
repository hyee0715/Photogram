package com.hy.photogram.web.api;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.domain.user.User;
import com.hy.photogram.handler.ex.CustomValidationApiException;
import com.hy.photogram.service.SubscribeService;
import com.hy.photogram.service.UserService;
import com.hy.photogram.web.dto.auth.CMRespDto;
import com.hy.photogram.web.dto.subscribe.SubscribeDto;
import com.hy.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    /* 프로필 검색 */
    @GetMapping("/api/search/{name}")
    public ResponseEntity<?> searchProfile(@PathVariable String name) {
        List<User> userEntityList = userService.search(name);

        return new ResponseEntity<>(new CMRespDto<>(1, "사용자 프로필 검색 성공", userEntityList), HttpStatus.OK);
    }

    /* 회원정보 수정 */
    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable Long id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User userEntity = userService.update(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);

        return new CMRespDto<>(1, "회원수정 완료", userEntity);
    }

    /* 사용자 구독 목록 출력 */
    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독 정보 목록 가져오기 성공", subscribeDto), HttpStatus.OK);
    }

    /* 사용자 프로필 이미지 변경 */
    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUpdate(@PathVariable Long principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User userEntity = userService.profileImageUpdate(principalId, profileImageFile);

        principalDetails.setUser(userEntity);

        return new ResponseEntity<>(new CMRespDto<>(1, "프로필 사진 변경 성공", null), HttpStatus.OK);
    }
}

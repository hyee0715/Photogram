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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable Long id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성 검사에 실패하였습니다.", errorMap);
        }

        User userEntity = userService.update(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);

        return new CMRespDto<>(1, "회원수정 완료", userEntity);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독 정보 목록 가져오기 성공", subscribeDto), HttpStatus.OK);
    }
}

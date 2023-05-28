package com.hy.photogram.web.api;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.service.SubscribeService;
import com.hy.photogram.web.dto.auth.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    /* 구독하기 */
    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long toUserId) {
        subscribeService.subs(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 완료", null), HttpStatus.OK);
    }

    /* 구독취소 */
    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long toUserId) {
        subscribeService.unSubs(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독취소 완료", null), HttpStatus.OK);
    }
}
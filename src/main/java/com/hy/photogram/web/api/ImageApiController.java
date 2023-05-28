package com.hy.photogram.web.api;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.domain.image.Image;
import com.hy.photogram.service.ImageService;
import com.hy.photogram.service.LikesService;
import com.hy.photogram.web.dto.auth.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    /* 스토리 페이지 */
    @GetMapping("/api/image")
    public ResponseEntity<?> story(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                   @PageableDefault(size = 3) Pageable pageable) {
        Page<Image> images = imageService.story(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "이미지 스토리 불러오기 성공", images), HttpStatus.OK);
    }

    /* 게시글 좋아요 */
    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    /* 게시글 좋아요 해제 */
    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unLikes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }
}
package com.hy.photogram.web;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.domain.image.Image;
import com.hy.photogram.handler.ex.CustomValidationException;
import com.hy.photogram.service.ImageService;
import com.hy.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    /* 스토리 페이지 */
    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    /* 인기 페이지 */
    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<Image> images = imageService.popular();
        model.addAttribute("images", images);

        return "image/popular";
    }

    /* 이미지 업로드 페이지 */
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    /* 이미지 업로드 */
    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미자가 첨부되지 않았습니다.", null);
        }

        imageService.upload(imageUploadDto, principalDetails);

        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}

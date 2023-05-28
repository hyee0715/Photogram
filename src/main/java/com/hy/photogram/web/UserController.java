package com.hy.photogram.web;

import com.hy.photogram.config.auth.PrincipalDetails;
import com.hy.photogram.service.UserService;
import com.hy.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    /* 사용자 프로필 페이지 */
    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable Long pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto userProfileDto = userService.profile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", userProfileDto);

        return "user/profile";
    }

    /* 회원정보 수정 페이지 */
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id) {
        return "user/update";
    }

    @GetMapping("/user/search")
    public String search() {
        return "user/search";
    }
}

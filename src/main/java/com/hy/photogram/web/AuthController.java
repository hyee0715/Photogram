package com.hy.photogram.web;

import com.hy.photogram.domain.user.User;
import com.hy.photogram.service.AuthService;
import com.hy.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller     //IoC, 파일을 리턴하는 Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {     //검사 결과 유효하지 않은 데이터가 발견된 경우 실행
        //SignupDto -> User
        User user = signupDto.toEntity();
        authService.join(user);

        return "auth/signin";
    }
}
